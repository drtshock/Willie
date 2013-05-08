package com.drtshock.willie;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Timer;

import org.pircbotx.Base64;
import org.pircbotx.PircBotX;
import org.pircbotx.exception.IrcException;
import org.pircbotx.exception.NickAlreadyInUseException;

import com.google.gson.Gson;
import com.google.gson.JsonParser;

public class Willie extends PircBotX {
	
	public static final Gson gson = new Gson();
	public static final JsonParser parser = new JsonParser();
	public static String GIT_AUTH;
	
	public Properties config;
	public JenkinsServer jenkins;
	
	private Willie(String[] channels) throws NickAlreadyInUseException, IOException, IrcException{
		super();
		
		File configFile = new File("config.txt");
		this.config = new Properties();
		
		if (!configFile.exists()){
			this.config.put("github-username", "change-me");
			this.config.put("github-password", "change-me");
			this.config.store(new FileOutputStream(configFile), null);
		}else{
			this.config.load(new FileInputStream(configFile));
		}
		
		GIT_AUTH = "Basic " + Base64.encodeToString((this.config.getProperty("github-username") + ":" + this.config.getProperty("github-password")).getBytes(), false);
		
		this.jenkins = new JenkinsServer("http://ci.drtshock.com/");
		
		this.setName("Willie");
		this.setVerbose(false);
		this.getListenerManager().addListener(new CommandListener(this));
		this.connect("irc.esper.net");
		this.setAutoReconnectChannels(true);
		
		for (String channel : channels){
			this.joinChannel(channel);
		}
		
		(new Timer()).schedule(new IssueNotifierTask(this), 300000, 300000); // 5 minutes
	}
	
	public static void main(String[] args){
		try{
			new Willie(new String[]{"#drtshock"});
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	
}
