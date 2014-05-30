package com.drtshock.willie.command.minecraft;

import com.drtshock.willie.Willie;
import com.drtshock.willie.command.CommandHandler;
import com.drtshock.willie.util.WebHelper;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.pircbotx.Channel;
import org.pircbotx.Colors;
import org.pircbotx.User;

import java.net.URL;
import java.util.Map.Entry;

/**
 * @author stuntguy3000
 */
public class MCStatusCommandHandler implements CommandHandler {
    @Override
    public void handle(Willie bot, Channel channel, User sender, String[] args) throws Exception {
        String output = WebHelper.readURLToString(new URL("http://status.mojang.com/check"));
        JsonArray entries = (JsonArray) new JsonParser().parse(output);

        StringBuilder statusText = new StringBuilder();

        for (JsonElement entry : entries) {
            for (Entry<String, JsonElement> parsedEntry : entry.getAsJsonObject().entrySet()) {
                final String serviceName = parsedEntry.getKey().replace(".mojang.com", "").replace(".minecraft.net", "");
                final boolean isOnline = parsedEntry.getValue().getAsString().equalsIgnoreCase("green");
                statusText.append(isOnline ? Colors.DARK_GREEN : Colors.RED);
                statusText.append(serviceName.substring(0, 1).toUpperCase() + serviceName.substring(1));
                statusText.append(isOnline ? " (Online)" : " (Offline)");
            }
            statusText.append(Colors.NORMAL + " | ");
        }

        // CI: Remove trailing " | " and send.
        channel.sendMessage(statusText.toString().substring(0, statusText.length() - 3));
    }
}
