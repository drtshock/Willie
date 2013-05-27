package com.drtshock.willie.command;

import org.pircbotx.Channel;
import org.pircbotx.Colors;
import org.pircbotx.User;

import com.drtshock.willie.Willie;

public class RepoCommandHandler implements CommandHandler {
	
	@Override
	public void handle(Willie bot, Channel channel, User sender, String[] args){
		channel.sendMessage(Colors.BLUE + "Contribute if you feel so led: " + bot.getConfig().getBotSourceUrl());
	}
	
}
