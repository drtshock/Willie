package com.drtshock.willie.command.utility;


import org.pircbotx.Channel;
import org.pircbotx.Colors;
import org.pircbotx.User;

import com.drtshock.willie.Willie;
import com.drtshock.willie.command.CommandHandler;

/**
 * 
 * @author 3PlayingGames
 *
 */
public class DefineCommandHandler implements CommandHandler {

    @Override
    public void handle(Willie bot, Channel channel, User sender, String[] args) {
        if (args.length == 0) {
                int Min = 0;
                int Max = 100;
                int number = r.nextInt(High-Low) + Low;
                channel.sendMessessage(Color.RED + number);
        }
    }

}
