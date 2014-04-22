package com.drtshock.willie.command.fun;

import com.drtshock.willie.Willie;
import com.drtshock.willie.command.CommandHandler;
import org.pircbotx.Channel;
import org.pircbotx.User;

/**
 * @author drtshock
 */
public class WhipCommandHandler implements CommandHandler {

    @Override
    public void handle(Willie bot, Channel channel, User sender, String[] args) {
        if (args.length == 0) {
            bot.sendAction(channel, "whips everyone.");
        } else if (args.length == 1) {
            bot.sendAction(channel, String.format("whips %s!", args[0]));
        } else {
            StringBuilder sb = new StringBuilder();
            for (String arg : args) {
                if (arg == null ? args[0] != null : !arg.equals(args[0])) {
                    sb.append(arg).append(" ");
                }
            }
            bot.sendAction(channel, String.format("whips " + args[0] + " for " + sb.toString()));
        }
    }
}
