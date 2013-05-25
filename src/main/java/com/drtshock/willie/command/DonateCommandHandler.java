package com.drtshock.willie.command;

import java.util.ArrayList;
import java.util.Random;
import org.pircbotx.Channel;
import org.pircbotx.Colors;
import org.pircbotx.User;

import com.drtshock.willie.Willie;

public class DonateCommandHandler implements CommandHandler {
	
	private Random rand;
	private ArrayList<String> messages;
	
	public DonateCommandHandler(){
		this.rand = new Random();
		this.messages = new ArrayList<String>();
		
		this.messages.add(Colors.DARK_GREEN + "Buy me food :3 http://tinyurl.com/drtdonate");
		this.messages.add(Colors.DARK_GREEN + "Donations are nice. http://tinyurl.com/drtdonate");
		this.messages.add(Colors.DARK_GREEN + "Hey there http://tinyurl.com/drtdonate");
		this.messages.add(Colors.DARK_GREEN + "Buy dirt for drt http://tinyurl.com/drtdonate");
	}
	
	@Override
	public void handle(Willie bot, Channel channel, User sender, String[] args){
		channel.sendMessage(this.messages.get(this.rand.nextInt(this.messages.size())));
	}
	
}
