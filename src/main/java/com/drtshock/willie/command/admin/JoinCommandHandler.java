package com.drtshock.willie.command.admin;

import com.drtshock.willie.Willie;
import com.drtshock.willie.command.CommandHandler;
import org.pircbotx.Channel;
import org.pircbotx.Colors;
import org.pircbotx.User;

public class JoinCommandHandler implements CommandHandler {

    @Override
    public void handle(Willie bot, Channel channel, User sender, String[] args) {
        if (args.length > 2) {
            return;
        }
        if (!args[0].startsWith("#")) {
            args[0] = ("#" + args[0]).toLowerCase();
        }

        if (bot.isOnChannel(args[0])) {
            channel.send().message(String.format(Colors.GREEN + "Already there!"));
        } else {
            channel.send().message(Colors.GREEN + String.format("See you in %s!", args[0]));
            bot.joinChannel(args[0]);
            if (args.length == 2 && args[1].equalsIgnoreCase("silent")) {
                return;
            }
            bot.getChannel(args[0]).send().message(Colors.GREEN + String.format("%s told me I belong here.", sender.getNick()));
        }
    }
}
