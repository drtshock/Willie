package com.drtshock.willie.command.fun;

import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.pircbotx.Channel;
import org.pircbotx.User;

import spark.utils.IOUtils;

import com.drtshock.willie.Willie;
import com.drtshock.willie.command.CommandHandler;

public class SkypeResolverCommandHandler implements CommandHandler {
	@Override
	public void handle(Willie bot, Channel channel, User sender, String[] args) throws Exception {
		if(args.length == 0){
			sender.sendMessage("Please specify a Skype username!");
		}
		else{
			HttpURLConnection resolveConn = null;
			try {
				resolveConn = (HttpURLConnection) new URL("http://resolveme.org/api.php?key=51e5b3ddc8a36&method=0&skypePseudo=" + args[0]).openConnection();
				resolveConn.addRequestProperty("User-Agent", "Mozilla/4.76"); 
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
			
			StringWriter stringWriter = new  StringWriter(); 
			IOUtils.copy(resolveConn.getInputStream(), stringWriter); 
			String ipList = stringWriter.toString();
			
			sender.sendMessage("IP(s): " + ipList);
		}
	}
	
}
