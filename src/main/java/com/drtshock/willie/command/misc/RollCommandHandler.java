package com.drtshock.willie.command.misc;

import com.drtshock.willie.Willie;
import com.drtshock.willie.command.CommandHandler;
import java.util.Random;
import org.pircbotx.Channel;
import org.pircbotx.User;

public class RollCommandHandler implements CommandHandler {

    @Override
    public void handle(Willie bot, Channel channel, User sender, String[] args) {
        int sides = 6;
        if (args.length > 0) {
            try {
                sides = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
            }
            if (sides < 1) {
                bot.sendMessage(channel, sender.getNick() + ": You can't roll a die with less than 1 side!");
                return;
            }
        }
        Random r = new Random();
        int i = r.nextInt(sides) + 1;
        String extra = (sides == 6) ? "" : (Integer.toString(sides) + "-sided die for ");
        bot.sendAction(channel, " rolls a " + extra + i + "!");
    }
}
