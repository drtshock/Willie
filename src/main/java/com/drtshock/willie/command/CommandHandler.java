package com.drtshock.willie.command;

import org.pircbotx.Channel;
import org.pircbotx.User;

import com.drtshock.willie.Willie;

public interface CommandHandler {

    public void handle(Willie bot, Channel channel, User sender, String[] args) throws Exception;

}
