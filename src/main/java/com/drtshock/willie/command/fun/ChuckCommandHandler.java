package com.drtshock.willie.command.fun;

import com.drtshock.willie.Willie;
import com.drtshock.willie.command.CommandHandler;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
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
public class ChuckCommandHandler implements CommandHandler {

    @Override
    public void handle(Willie bot, Channel channel, User sender, String[] args) {
        try {
            channel.send().message(getQuote("http://api.icndb.com/jokes/random?limitTo=[nerdy]"));
        } catch (IOException e) {
            channel.send().message("Error occured! Chuck Norris is too awesome for me to handle.");
            e.printStackTrace();
        }
    }

    private String getQuote(String urlString) throws IOException {
        // Get page
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
        }

        String page = buffer.toString();

        input.close();

        // JSON - Get the quote
        JsonObject o = new JsonParser().parse(page).getAsJsonObject();
        JsonObject value = o.getAsJsonObject("value").getAsJsonObject();

        page = value.get("joke").getAsString();

        return page;
    }
}
