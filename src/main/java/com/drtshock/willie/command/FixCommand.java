package com.drtshock.willie.command;

import org.pircbotx.Channel;
import org.pircbotx.User;

import com.drtshock.willie.Willie;


public class FixCommand implements CommandHandler {

    @Override
    public void handle(Willie bot, Channel channel, User sender, String[] args) {
        if(args.length != 1) {
            channel.sendMessage("Fix it!");
        } else {
            channel.sendMessage(String.format("Fix it %s!", args[0]));
        }
    }

}
