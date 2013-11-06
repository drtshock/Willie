package com.drtshock.willie.command.fun;

import com.drtshock.willie.Willie;
import com.drtshock.willie.command.CommandHandler;
import org.pircbotx.Channel;
import org.pircbotx.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author stuntguy3000
 */
public class QuoteCommandHandler implements CommandHandler {

    @Override
    public void handle(Willie bot, Channel channel, User sender, String[] args) {
        try {
            channel.send().message(getPage("http://www.iheartquotes.com/api/v1/random?show_permalink=false"));
        } catch (IOException e) {
            channel.send().message("Error fetching quote");
            e.printStackTrace();
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
            if (!line.startsWith("[")) {
                buffer.append(line.replace("&quot;", ""));
                buffer.append('\n');
            }
        }

        String page = buffer.toString();

        input.close();

        return page;
    }
}
