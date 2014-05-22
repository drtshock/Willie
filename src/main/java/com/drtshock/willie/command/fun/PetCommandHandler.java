package com.drtshock.willie.command.fun;

import com.drtshock.willie.Willie;
import com.drtshock.willie.command.CommandHandler;
import org.pircbotx.Channel;
import org.pircbotx.User;

public class PetCommandHandler implements CommandHandler {

    @Override
    public void handle(Willie bot, Channel channel, User sender, String[] args) {
        if (args.length == 0) {
            bot.sendAction(channel, "pets everyone!");
        } else if (args.length == 1) {
            bot.sendAction(channel, "pets " + args[0]);
        } else {
            bot.sendAction(channel, "Please use !pet or !pet <person>!");
        }
    }
}
