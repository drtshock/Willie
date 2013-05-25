package com.drtshock.willie.command;

import java.util.ArrayList;
import java.util.Random;

import org.pircbotx.Channel;
import org.pircbotx.User;

import com.drtshock.willie.Willie;

public class PopcornCommandHandler implements CommandHandler {
	
	private Random rand;
	private ArrayList<String> messages;
	
	public PopcornCommandHandler(){
		this.rand = new Random();
		this.messages = new ArrayList<String>();
		
		this.messages.add("pops some plain popcorn.");
		this.messages.add("pops some popcorn with butter.");
		this.messages.add("pops some popcorn with salt and butter!");
		this.messages.add("pops some kettle corn.");
	}
	
	@Override
	public void handle(Willie bot, Channel channel, User sender, String[] args){
		bot.sendAction(channel, this.messages.get(this.rand.nextInt(this.messages.size())));
	}
	
}
