package com.drtshock.willie.command;

import org.pircbotx.Channel;
import org.pircbotx.User;

public interface CommandHandler {
	
	public void handle(Channel channel, User sender, String[] args);
	
}
