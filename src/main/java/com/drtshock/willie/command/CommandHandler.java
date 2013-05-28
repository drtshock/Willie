package com.drtshock.willie.command;

import com.drtshock.willie.Willie;
import org.pircbotx.Channel;
import org.pircbotx.User;

public interface CommandHandler {

    public void handle(Willie bot, Channel channel, User sender, String[] args);

}
