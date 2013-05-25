package com.drtshock.willie.command;

import org.pircbotx.Channel;
import org.pircbotx.User;

import com.drtshock.willie.Willie;

public class TWSSCommandHandler implements CommandHandler {
	
	@Override
	public void handle(Willie bot, Channel channel, User sender, String[] args){
		channel.sendMessage("That's what she said!");
	}
	
}
