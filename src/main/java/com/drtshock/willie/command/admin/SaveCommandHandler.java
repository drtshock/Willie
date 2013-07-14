package com.drtshock.willie.command.admin;

import org.pircbotx.Channel;
import org.pircbotx.Colors;
import org.pircbotx.User;

import com.drtshock.willie.Willie;
import com.drtshock.willie.command.CommandHandler;

public class SaveCommandHandler implements CommandHandler{
	@Override
	public void handle(Willie bot, Channel channel, User sender, String[] args){
		bot.save();
		channel.sendMessage(Colors.GREEN + "Configuration saved!");
	}
}
