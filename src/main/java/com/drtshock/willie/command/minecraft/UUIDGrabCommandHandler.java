package com.drtshock.willie.command.minecraft;

import com.drtshock.willie.Willie;
import com.drtshock.willie.command.CommandHandler;
import org.json.JSONObject;
import org.pircbotx.Channel;
import org.pircbotx.User;

import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

/**
 * @author stuntguy3000
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

    public static String[] getUUID(String player) {
        try {
            // Get the UUID from SwordPVP
            URL url = new URL("https://uuid.swordpvp.com/uuid/" + player);
            URLConnection uc = url.openConnection();
            uc.setUseCaches(false);
            uc.setDefaultUseCaches(false);
            uc.addRequestProperty("User-Agent", "Mozilla/5.0");
            uc.addRequestProperty("Cache-Control", "no-cache, no-store, must-revalidate");
            uc.addRequestProperty("Pragma", "no-cache");

            String json = new Scanner(uc.getInputStream(), "UTF-8").useDelimiter("\\A").next();
            JSONObject object = new JSONObject(json).getJSONArray("profiles").getJSONObject(0);
            return new String[]{object.getString("name"), object.getString("id")};
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

}
