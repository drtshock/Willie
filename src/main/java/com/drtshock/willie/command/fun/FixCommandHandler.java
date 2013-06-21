package com.drtshock.willie.command.fun;

import com.drtshock.willie.Willie;
import com.drtshock.willie.command.CommandHandler;
import org.pircbotx.Channel;
import org.pircbotx.User;


public class FixCommandHandler implements CommandHandler {

    @Override
    public void handle(Willie bot, Channel channel, User sender, String[] args) {
        if(args.length != 1) {
            channel.sendMessage("Fix it!");
        } else {
            channel.sendMessage(String.format("Fix it %s!", args[0]));
        }
    }

}
