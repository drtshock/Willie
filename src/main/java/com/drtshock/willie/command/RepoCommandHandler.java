package com.drtshock.willie.command;

import org.pircbotx.Channel;
import org.pircbotx.Colors;
import org.pircbotx.User;

public class RepoCommandHandler implements CommandHandler {
	
	@Override
	public void handle(Channel channel, User sender, String[] args){
		channel.sendMessage(Colors.BLUE + "Contribute if you feel so led: https://github.com/drtshock/willie");
	}
	
}
