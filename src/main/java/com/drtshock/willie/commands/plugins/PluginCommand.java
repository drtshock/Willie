package com.drtshock.willie.commands.plugins;

import com.drtshock.willie.Willie;
import com.drtshock.willie.command.Command;
import com.drtshock.willie.command.CommandType;
import com.drtshock.willie.command.ICommand;
import com.drtshock.willie.util.StringTools;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.pircbotx.Channel;
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

public class PluginCommand implements ICommand {

    private SimpleDateFormat dateFormat;

    public PluginCommand() {
        this.dateFormat = new SimpleDateFormat("YYYY-MM-dd");
    }

    public void handle(Willie bot, Command cmd, CommandType type, Channel channel, User sender, String[] args) {
        if (args.length != 1) {
            bot.send(cmd.getHelp(), channel, sender, type);
            return;
        }

        try {
            URL url = new URL("http://dev.bukkit.org/projects/" + args[0] + "/");

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setConnectTimeout(10000);
            connection.setReadTimeout(10000);
            connection.setUseCaches(false);

            BufferedReader input = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            StringBuilder buffer = new StringBuilder();
            String line;

            while ((line = input.readLine()) != null) {
                buffer.append(line);
                buffer.append('\n');
            }

            String page = buffer.toString();

            input.close();

            Document document = Jsoup.parse(page);

            String name = document.getElementsByTag("h1").get(1).ownText().trim();
            StringBuilder authors = new StringBuilder();
            int downloads = Integer.parseInt(document.getElementsByAttribute("data-value").first().attr("data-value"));

            Elements containers = document.getElementsByClass("user-container");

            if (!containers.isEmpty()) {
                authors.append(StringTools.noPing(containers.get(0).text().trim()));
            }

            for (int i = 1; i < containers.size(); ++i) {
                authors.append(", ");
                String author = containers.get(i).text().trim();
                authors.append(StringTools.noPing(author));
            }

            String files;
            Elements filesList = document.getElementsByClass("file-type");
            if (filesList.size() > 0) {
                Element latest = filesList.get(0);
                String version = latest.nextElementSibling().ownText();
                String bukkitVersion = latest.parent().ownText().split("for")[1].trim();
                long fileDate = Long.parseLong(latest.nextElementSibling().nextElementSibling().attr("data-epoch"));
                String date = this.dateFormat.format(new Date(fileDate * 1000));
                files = "Latest File: " + version + " for " + bukkitVersion + " (" + date + ")";
            } else {
                files = "No files (yet!)";
            }

            bot.send(name + " (" + connection.getURL().toExternalForm() + ")", channel, sender, type);
            bot.send("Authors: " + authors.toString(), channel, sender, CommandType.NOTICE_SENDER);
            bot.send("Downloads: " + downloads, channel, sender, CommandType.NOTICE_SENDER);
            bot.send(files, channel, sender, CommandType.NOTICE_SENDER);
        } catch (FileNotFoundException e) {
            bot.send("Project not found.", channel, sender, type);
        } catch (MalformedURLException e) {
            bot.send("Project not found.", channel, sender, type);
        } catch (IOException e) {
            bot.send("Failed: " + e.getMessage(), channel, sender, type);
        }
    }
}