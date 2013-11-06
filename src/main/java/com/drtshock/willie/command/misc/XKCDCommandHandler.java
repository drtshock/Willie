package com.drtshock.willie.command.misc;

import com.drtshock.willie.Willie;
import com.drtshock.willie.command.CommandHandler;
import com.drtshock.willie.util.WebHelper;
import org.pircbotx.Channel;
import org.pircbotx.Colors;
import org.pircbotx.User;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

public class XKCDCommandHandler implements CommandHandler {

    private static final Logger LOG = Logger.getLogger(XKCDCommandHandler.class.getName());

    @Override
    public void handle(Willie bot, Channel channel, User sender, String[] args) throws Exception {
        if (args.length != 1) {
            nope(channel);
            return;
        }

        try {
            final String arg = args[0].toLowerCase();
            String prefix;
            String url;
            try {
                final int number = Integer.parseInt(arg);
                url = "http://xkcd.com/" + number + "/";
                prefix = "xkcd comic number " + number + ": ";

                // Just check if valid
                getPage(url);
            } catch (NumberFormatException e) {
                url = "http://dynamic.xkcd.com/random/comic/";
                prefix = "Random xkcd: ";
            }
            final String shortImgUrl = WebHelper.shortenURL(url);
            channel.send().message(prefix + shortImgUrl);
        } catch (FileNotFoundException | MalformedURLException | IndexOutOfBoundsException |
                SocketTimeoutException e) {
            LOG.log(Level.INFO, "Can't get that xkcd comic. Does it exist?", e);
            channel.send().message(Colors.RED + "Can't get that xkcd comic. Does it exist?");
        } catch (IOException e) {
            channel.send().message(Colors.RED + "Failed: " + e.getMessage());
            throw e; // Gist
        }
    }

    private String getPage(String urlString) throws IOException {
        URL url = new URL(urlString);

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

        return page;
    }

    public void nope(Channel channel) {
        channel.send().message(Colors.RED + "Get an xkcd comic with !xkcd <nb>");
    }
}
