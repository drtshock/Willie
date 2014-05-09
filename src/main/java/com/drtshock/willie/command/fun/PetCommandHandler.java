package com.drtshock.willie.command.fun;

import com.drtshock.willie.Willie;
import com.drtshock.willie.command.CommandHandler;
import org.pircbotx.Channel;
import org.pircbotx.User;

public class PetSnacksCommandHandler implements CommandHandler {

    @Override
    public void handle(Willie bot, Channel channel, User sender, String[] args) {
        if(args.length==0 || args.length >= 2) {
          bot.sendAction(channel, "Please use '!pet <person>'!");
          return;
        }
          String person = args[0];
          bot.sendAction(channel, "pets " + person);
          
    }
}
