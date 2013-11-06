package com.drtshock.willie.command.misc;

import com.drtshock.willie.Willie;
import com.drtshock.willie.command.CommandHandler;
import org.pircbotx.Channel;
import org.pircbotx.User;

public class PokeCommandHandler implements CommandHandler {

    @Override
    public void handle(Willie bot, Channel channel, User sender, String[] args) {
        String poke = "everyone";
        if (args.length >= 1) {
            poke = args[0];
        }
        channel.send().message("pokes " + poke);
    }
}
