package com.drtshock.willie.command.admin;

import org.pircbotx.Channel;
import org.pircbotx.User;

import com.drtshock.willie.Willie;
import com.drtshock.willie.command.CommandHandler;

/**
 * 
 * @author drtshock
 */
public class PrefixCommandHandler implements CommandHandler{

	@Override
	public void handle(Willie bot, Channel channel, User sender, String[] args){
		if(args.length == 0){
			channel.sendMessage("Use that to define a new command prefix.");
		}else if(args.length == 1){
			bot.getConfig().setCommandPrefix(args[0]);
			channel.sendMessage(String.format("Set command prefix to \"%s\"", args[0]));
		}
	}
}
