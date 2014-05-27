package com.drtshock.willie.command.minecraft;

import com.drtshock.willie.Willie;
import com.drtshock.willie.command.CommandHandler;
import org.pircbotx.Channel;
import org.pircbotx.User;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;

/**
 * @author ParaPenguin
 */
public class UUIDGrabCommandHandler implements CommandHandler {

    @Override
    public void handle(Willie bot, Channel channel, User sender, String[] args) throws Exception {
        if (args.length == 0) {
            bot.sendAction(channel, "No Username specified");
            return;
        }

        for(String arg : args) {
            String name = arg;
            String uuid;

            try {
                String[] result = getUUID(arg);
                if(result == null) {
                    uuid = "Invalid";
                } else {
                    name = result[0];
                    uuid = result[1];
                }
            } catch (Exception e) {
                uuid = "Invalid";
            }

            bot.sendAction(channel, name + ": " + uuid);
        }
    }

    public String[] getUUID(String name) {
        try {
            ProfileData profC = new ProfileData(name);
            PlayerProfile[] result = post(new URL("https://api.mojang.com/profiles/minecraft"), Proxy.NO_PROXY, Willie.gson.toJson(profC).getBytes());
            name = result[1].getId();
            String uuid = result[0].getId();
            return new String[]{name, uuid};
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private PlayerProfile[] post(URL url, Proxy proxy, byte[] bytes) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection(proxy);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoInput(true);
        connection.setDoOutput(true);

        DataOutputStream out = new DataOutputStream(connection.getOutputStream());
        out.write(bytes);
        out.flush();
        out.close();

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuffer response = new StringBuffer();
        String line;
        while((line = reader.readLine()) != null) {
            response.append(line);
            response.append('\r');
        }
        reader.close();
        return Willie.gson.fromJson(response.toString(), SearchResult.class).getProfiles();
    }

    private class PlayerProfile {
        private String id;

        public String getId() {
            return id;
        }
    }

    private class SearchResult {
        private PlayerProfile[] profiles;

        public PlayerProfile[] getProfiles() {
            return profiles;
        }
    }

    private static class ProfileData {

        private String name;
        private String agent = "minecraft";

        public ProfileData(String name) {
            this.name = name;
        }
    }

}
