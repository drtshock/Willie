package com.drtshock.willie.command.admin;

import com.drtshock.willie.Willie;
import com.drtshock.willie.command.CommandHandler;
import org.pircbotx.Channel;
import org.pircbotx.User;

/**
 * @author drtshock
 */
public class PrefixCommandHandler implements CommandHandler {

    @Override
    public void handle(Willie bot, Channel channel, User sender, String[] args) {
        if (args.length == 0) {
            channel.send().message("Use that to define a new command prefix.");
        } else if (args.length == 1) {
            bot.getConfig().setCommandPrefix(args[0]);
            channel.send().message(String.format("Set command prefix to \"%s\"", args[0]));
        }
    }
}
