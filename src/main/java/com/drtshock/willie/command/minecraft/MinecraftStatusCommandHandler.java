package com.drtshock.willie.command.minecraft;

import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.pircbotx.Channel;
import org.pircbotx.User;

import spark.utils.IOUtils;

import com.drtshock.willie.Willie;
import com.drtshock.willie.command.CommandHandler;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class MinecraftStatusCommandHandler implements CommandHandler {

	@Override
	public void handle(Willie bot, Channel channel, User sender, String[] args) throws Exception {
		if(args.length == 0){
			sender.sendMessage("Please specify a Server IP!");
		}
		else{
			HttpURLConnection statusConn = null;
			try {
				statusConn = (HttpURLConnection) new URL("http://minecraft-api.com/v1/get/?server=" + args[0]).openConnection();
				statusConn.addRequestProperty("User-Agent", "Mozilla/4.76"); 
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			StringWriter stringWriter = new  StringWriter(); 
			IOUtils.copy(statusConn.getInputStream(), stringWriter); 
			String statusJson = stringWriter.toString();
			
		    JsonObject serverStuff = new JsonParser().parse(statusJson).getAsJsonObject();
			
			if(serverStuff.get("status").getAsBoolean() == false){
				sender.sendMessage(args[0] + " is: Offline");
			}
			else{
				sender.sendMessage(args[0] + " is: Online");
				sender.sendMessage("It has: " + serverStuff.get("players").getAsJsonObject().get("online") + "/" + serverStuff.get("players").getAsJsonObject().get("max") + " players");
				sender.sendMessage("They also happen to be running on: " + serverStuff.get("version").getAsString());
			}
		}
	}

}
