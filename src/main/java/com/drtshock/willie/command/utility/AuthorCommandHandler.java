package com.drtshock.willie.command.utility;

import com.drtshock.willie.Willie;
import com.drtshock.willie.command.CommandHandler;
import com.drtshock.willie.util.Tools;
import com.drtshock.willie.util.WebHelper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.pircbotx.Channel;
import org.pircbotx.Colors;
import org.pircbotx.User;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AuthorCommandHandler implements CommandHandler {

    private static final Logger LOG = Logger.getLogger(AuthorCommandHandler.class.getName());
    private SimpleDateFormat dateFormat;

    public AuthorCommandHandler() {
        this.dateFormat = new SimpleDateFormat("YYYY-MM-dd");
    }

    @Override
    public void handle(Willie bot, Channel channel, User sender, String[] args) throws Exception {
        LOG.log(Level.INFO, "Started to handle !author command from {0}...", sender.getNick());
        if (args.length != 1 && args.length != 2) {
            nope(channel);
            return;
        }

        try {
            int amount = 3;
            if (args.length == 2) {
                try {
                    amount = Integer.parseInt(args[1]);
                } catch (NumberFormatException e) {
                    nope(channel);
                    return;
                }

                if (amount <= 0) {
                    nope(channel);
                    return;
                }
            }
            LOG.log(Level.INFO, "Selected amount: {0}", amount);

            LOG.log(Level.INFO, "Provided username: {0}", args[0]);
            UserInfo user = getRealUserName(args[0]);
            LOG.log(Level.INFO, "Real username: {0}", user.name);

            SortedSet<Plugin> plugins = new TreeSet<>();
            boolean hasNextPage;
            Document document;
            String devBukkitLink = "http://dev.bukkit.org/";
            String profilePageLink = devBukkitLink + "profiles/" + user.name;
            String nextPageLink = profilePageLink + "/bukkit-plugins/";
            do {
                // Get the page
                LOG.log(Level.INFO, "Getting page \"{0}\"...", nextPageLink);
                document = getPage(nextPageLink);

                // Check if there is at least one plugin
                if (document.getElementsByClass("listing-none-found").size() > 0) {
                    channel.sendMessage(Tools.silence(user.name) + " - " + user.state + " (" + WebHelper.shortenURL(profilePageLink) + ") | Reputation: " + user.reputation + " | No Project");
                    channel.sendMessage("Join date: " + user.joined + " | Status: " + user.lastLogin);
                    if (user.state.contains("Banned")) {
                        channel.sendMessage("Ban reason: " + user.banReason);
                    }
                    return;
                }

                // Check if we will have to look at another page
                Elements pages = document.getElementsByClass("listing-pagination-pages").get(0).children();
                if (pages.size() > 1) {
                    Element lastLink = pages.get(pages.size() - 1);
                    if (lastLink.children().size() > 0 && lastLink.child(0).ownText().trim().startsWith("Next")) {
                        hasNextPage = true;
                        nextPageLink = devBukkitLink + lastLink.child(0).attr("href");
                    } else {
                        hasNextPage = false;
                        nextPageLink = null;
                    }
                } else {
                    hasNextPage = false;
                    nextPageLink = null;
                }

                // List stuff on this page
                Plugin plugin;
                String date;
                Elements pluginsTd = document.getElementsByClass("col-project");
                for (Element e : pluginsTd) {
                    if ("td".equalsIgnoreCase(e.tagName())) {
                        plugin = new Plugin();
                        plugin.name = e.getElementsByTag("h2").get(0).getElementsByTag("a").get(0).ownText().trim();
                        date = e.nextElementSibling().child(0).attr("data-epoch");
                        try {
                            plugin.lastUpdate = Long.parseLong(date);
                        } catch (NumberFormatException ex) {
                            channel.sendMessage(Colors.RED + "An error occured: Cannot parse \"" + date + "\" as a long.");
                            return;
                        }
                        LOG.log(Level.INFO, "Adding plugin {0}", plugin.name);
                        plugins.add(plugin);
                    }
                }
            } while (hasNextPage);

            Iterator<Plugin> it = plugins.iterator();

            channel.sendMessage(Tools.silence(user.name) + " - " + user.state + " (" + WebHelper.shortenURL(profilePageLink) + ") | Reputation: " + user.reputation + " | Projects: " + plugins.size());
            channel.sendMessage("Join date: " + user.joined + " | Status: " + user.lastLogin);
            if (user.state.contains("Banned")) {
                channel.sendMessage("Ban reason: " + user.banReason);
            }
            if (plugins.isEmpty()) { // Should not happen
                channel.sendMessage(Colors.RED + "Unknown user or user without plugins");
            } else if (amount == 1) {
                Plugin plugin = it.next();
                channel.sendMessage("Last updated plugin: " + Tools.silence(plugin.name) + " (" + formatDate(plugin.lastUpdate) + ")");
            } else {
                channel.sendMessage((amount < plugins.size() ? amount : plugins.size()) + " last updated plugins:");
                int i = 0;
                while (it.hasNext() && i < amount) {
                    Plugin plugin = it.next();
                    channel.sendMessage("- " + Tools.silence(plugin.name) + " (" + formatDate(plugin.lastUpdate) + ")");
                    i++;
                }
            }

            LOG.info("Command execution successful!");
        } catch (FileNotFoundException | MalformedURLException e) {
            channel.sendMessage(Colors.RED + "Unable to find that user!");
        } catch (IOException e) {
            channel.sendMessage(Colors.RED + "Failed: " + e.getMessage());
            throw e;
        }
    }

    private final class Plugin implements Comparable<Plugin> {

        public String name;
        public long lastUpdate;

        @Override
        public int compareTo(Plugin o) {
            return -Long.compare(lastUpdate, o.lastUpdate);
        }
    }

    private Document getPage(String urlString) throws IOException {
        URL url = new URL(urlString);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setConnectTimeout(10000);
        connection.setReadTimeout(10000);
        connection.setUseCaches(false);
        String page;
        try (BufferedReader input = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            StringBuilder buffer = new StringBuilder();
            String line;
            while ((line = input.readLine()) != null) {
                buffer.append(line);
                buffer.append("\n\n");
            }
            page = buffer.toString();
        }

        return Jsoup.parse(page);
    }

    private class UserInfo {

        public String name;
        public String state;
        public String joined;
        public String lastLogin;
        public String reputation;
        public String banReason;
    }

    private UserInfo getRealUserName(String bukkitDevUser) throws IOException {
        Document doc = getPage("http://dev.bukkit.org/profiles/" + bukkitDevUser);
        UserInfo info = new UserInfo();

        // Username
        info.name = doc.getElementsByTag("h1").get(1).ownText().trim();

        // User state
        if (doc.getElementsByClass("avatar-author").size() > 0) {
            info.state = Colors.DARK_BLUE + "Author";
        } else if (doc.getElementsByClass("avatar-normal").size() > 0) {
            info.state = Colors.DARK_GRAY + "Normal";
        } else if (doc.getElementsByClass("avatar-moderator").size() > 0) {
            info.state = Colors.DARK_GREEN + "Staff";
        } else if (doc.getElementsByClass("avatar-banned").size() > 0) {
            info.state = Colors.RED + "Banned";
        } else {
            info.state = Colors.PURPLE + "Unknown";
        }
        info.state += Colors.NORMAL;

        Elements elems = doc.getElementsByClass("content-box-inner");
        Element contentDiv = elems.get(elems.size() - 1);

        // User joined date
        String date = contentDiv.getElementsByClass("standard-date").get(0).attr("data-epoch");
        long dateLong = Long.parseLong(date);
        info.joined = formatDate(dateLong);

        // Last login
        if (contentDiv.getElementsByClass("user-online").size() > 0) {
            info.lastLogin = Colors.GREEN + "Online" + Colors.NORMAL;
        } else {
            date = contentDiv.getElementsByClass("user-offline").get(0).getElementsByClass("standard-date").get(0).attr("data-epoch");
            dateLong = Long.parseLong(date);
            info.lastLogin = Colors.DARK_GRAY + "Offline, last login on " + formatDate(dateLong) + Colors.NORMAL;
        }

        // Reputation
        info.reputation = contentDiv.getElementsByAttribute("data-value").get(0).ownText().trim();

        // Ban reason
        if (info.state.contains("Banned")) {
            info.banReason = doc.getElementsByClass("warning-message-inner").get(0).child(0).ownText().trim().substring(27);
        }
        return info;
    }

    private void nope(Channel channel) {
        channel.sendMessage(Colors.RED + "Look up an author with !author <name> [amount]");
    }

    private String formatDate(long date) {
        return this.dateFormat.format(new Date(date * 1000));
    }
}
