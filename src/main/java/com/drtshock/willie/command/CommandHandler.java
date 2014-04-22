package com.drtshock.willie.command;

import com.drtshock.willie.Willie;
import org.pircbotx.Channel;
import org.pircbotx.User;

import java.util.Random;

public interface CommandHandler {
    static final Random rand = new Random(); // Random instance so that you don't have to create one every time someone types a command.

    public void handle(Willie bot, Channel channel, User sender, String[] args) throws Exception;
}
