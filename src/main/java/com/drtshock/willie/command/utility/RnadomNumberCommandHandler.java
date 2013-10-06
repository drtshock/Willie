package com.drtshock.willie.command.utility;


import org.pircbotx.Channel;
import org.pircbotx.Colors;
import org.pircbotx.User;

import java.util.Random;

import com.drtshock.willie.Willie;
import com.drtshock.willie.command.CommandHandler;

/**
 * 
 * @author 3PlayingGames
 *
 */
public class RandomNumberCommandHandler implements CommandHandler {

    @Override
    public void handle(Willie bot, Channel channel, User sender, String[] args) {
        if (args.length == 0) {
                Random r = new Random();
                int Min = 0;
	              int Max = 100;
                bot.sendMessage("" + Color.RED + Min + (int)(Math.random() * ((Max - Min) + 1)));
                bot.sendAction(channel, "" + Color.RED + Min + (int)(Math.random() * ((Max - Min) + 1)));
        }
    }

}
