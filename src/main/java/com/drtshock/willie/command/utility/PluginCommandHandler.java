package com.drtshock.willie.command.utility;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Date;
import java.text.SimpleDateFormat;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.pircbotx.Channel;
import org.pircbotx.Colors;
import org.pircbotx.User;

import com.drtshock.willie.Willie;
import com.drtshock.willie.command.CommandHandler;

public class PluginCommandHandler implements CommandHandler {

    private SimpleDateFormat dateFormat;

    public PluginCommandHandler() {
        this.dateFormat = new SimpleDateFormat("EEEE dd MMMM YYYY");
    }

    @Override
    public void handle(Willie bot, Channel channel, User sender, String[] args) {
        if (args.length != 1) {
            channel.sendMessage(Colors.RED + "Look up a plugin with !plugin <name>");
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
            long lastUpdate = Long.parseLong(document.getElementsByClass("standard-date").get(1).attr("data-epoch"));
            int downloads = Integer.parseInt(document.getElementsByAttribute("data-value").first().attr("data-value"));

            Elements containers = document.getElementsByClass("user-container");

            if (!containers.isEmpty()) {
                authors.append(containers.get(0).text().trim());
            }
            
            char blankc = 0x200b;
            String blank = String.valueOf(blankc);

            for (int i = 1; i < containers.size(); ++i) {
                authors.append(", ");
                String author = containers.get(i).text().trim();
                // Insert blank character so people aren't pinged
                author = author.substring(0, 2) + blank + author.substring(2, author.length());
                authors.append(author);
            }

            channel.sendMessage(name + " (" + connection.getURL().toExternalForm() + ")");
            channel.sendMessage("Authors: " + authors.toString());
            channel.sendMessage("Downloads: " + downloads);
            channel.sendMessage("Last Update: " + this.dateFormat.format(new Date(lastUpdate * 1000)));
        } catch (FileNotFoundException e) {
            channel.sendMessage(Colors.RED + "Project not found");
        } catch (MalformedURLException e) {
            channel.sendMessage(Colors.RED + "Unable to find that plugin!");
        } catch (IOException e) {
            channel.sendMessage(Colors.RED + "Failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
