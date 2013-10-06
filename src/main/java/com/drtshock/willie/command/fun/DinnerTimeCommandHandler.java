package com.drtshock.willie.command.fun;

import org.pircbotx.Channel;
import org.pircbotx.User;

import com.drtshock.willie.Willie;
import com.drtshock.willie.command.CommandHandler;

public class DinnerTimeCommandHandler implements CommandHandler {

    @Override
    public void handle(Willie bot, Channel channel, User sender, String[] args) {
        bot.sendAction(channel, "ITS DINNERTIME!!!!!");
    }
}
