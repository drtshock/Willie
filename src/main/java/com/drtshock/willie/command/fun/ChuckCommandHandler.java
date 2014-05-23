package com.drtshock.willie.command.fun;

import com.drtshock.willie.Willie;
import com.drtshock.willie.command.CommandHandler;
import com.drtshock.willie.util.WebHelper;
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
            channel.sendMessage(getQuote("http://api.icndb.com/jokes/random?limitTo=[nerdy]"));
        } catch (IOException e) {
            channel.sendMessage("Error occured! Chuck Norris is too awesome for me to handle.");
            e.printStackTrace();
        }
    }

    private String getQuote(String urlString) throws IOException {
        // Get page
    	String page = WebHelper.readURLToString(new URL(urlString));

        // JSON - Get the quote
        JsonObject o = new JsonParser().parse(page).getAsJsonObject();
        JsonObject value = o.getAsJsonObject("value").getAsJsonObject();

        page = value.get("joke").getAsString();

        return page;
    }
}
