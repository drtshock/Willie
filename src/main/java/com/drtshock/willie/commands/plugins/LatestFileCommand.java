package com.drtshock.willie.commands.plugins;

import com.drtshock.willie.Willie;
import com.drtshock.willie.command.Command;
import com.drtshock.willie.command.CommandType;
import com.drtshock.willie.command.ICommand;
import com.drtshock.willie.util.WebHelper;
import org.pircbotx.Channel;
import org.pircbotx.Colors;
import org.pircbotx.User;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class LatestFileCommand implements ICommand {

    public void handle(Willie bot, Command cmd, CommandType type, Channel channel, User sender, String[] args) {
        if (args.length != 1) {
            bot.send(cmd.getHelp(), channel, sender, type);
        }

        try {
            URL url = new URL("http://dev.bukkit.org/projects/" + args[0] + "/files.rss");

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setConnectTimeout(10000);
            connection.setReadTimeout(10000);
            connection.setUseCaches(false);

            Document feed = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(connection.getInputStream());

            NodeList titles = feed.getElementsByTagName("title");
            NodeList links = feed.getElementsByTagName("link");

            if (titles.getLength() <= 1 || links.getLength() <= 1) {
                bot.send(args[0] + " has no files.", channel, sender, type);
                return;
            }

            String message = "Latest release of " + Colors.GREEN + args[0] + Colors.NORMAL + " is " + Colors.GREEN + titles.item(1).getTextContent()
                    + Colors.NORMAL + ": " + WebHelper.getShortenedURL(links.item(1).getTextContent());
            bot.send(message, channel, sender, type);
        } catch (FileNotFoundException e) {
            bot.send("Project not found.", channel, sender, type);
        } catch (SAXException e) {
            bot.send("Failed: " + e.getMessage(), channel, sender, type);
        } catch (MalformedURLException e) {
            bot.send("Failed: " + e.getMessage(), channel, sender, type);
        } catch (IOException e) {
            bot.send("Failed: " + e.getMessage(), channel, sender, type);
        } catch (ParserConfigurationException e) {
            bot.send("Something went wrong :(", channel, sender, type);
            e.printStackTrace();
        }
    }
}