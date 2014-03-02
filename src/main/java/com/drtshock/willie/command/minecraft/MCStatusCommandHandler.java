package com.drtshock.willie.command.minecraft;

import com.drtshock.willie.Willie;
import com.drtshock.willie.command.CommandHandler;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import org.pircbotx.Channel;
import org.pircbotx.Colors;
import org.pircbotx.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author stuntguy3000
 */
public class MCStatusCommandHandler implements CommandHandler {

    @Override
    public void handle(Willie bot, Channel channel, User sender, String[] args) throws Exception {
        HashMap<String, String> services = new HashMap<>();
        String output = getPage("http://status.mojang.com/check");
        JsonArray entries = (JsonArray) new JsonParser().parse(output);
        Iterator i = entries.iterator();

        int count = 0;
        // Poor code, but it works
        while (i.hasNext()) {
            String[] status = entries.get(count).getAsJsonObject().toString().replace("{", "").replace("}", "").replace("\"", "").replace(".mojang.com", "").replace(".minecraft.net", "").split(":");
            services.put(status[0].substring(0,1).toUpperCase() + status[0].substring(1), status[1]);

            i.next();
            count ++;
        }

        StringBuilder status = new StringBuilder();

        for (Map.Entry<String, String> service : services.entrySet()) {
            if (service.getValue().equalsIgnoreCase("green")) {
                status.append(Colors.DARK_GREEN + service.getKey() + " (Online) ");
            } else {
                status.append(Colors.RED + service.getKey() + " (Offline) ");
            }

            status.append(Colors.NORMAL + " | ");
        }

        channel.sendMessage(status.toString().substring(0, status.length() - 3));
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
        }

        String page = buffer.toString();

        input.close();

        return page;
    }
}
