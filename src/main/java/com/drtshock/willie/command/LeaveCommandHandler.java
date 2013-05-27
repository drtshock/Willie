package com.drtshock.willie.command;

import com.drtshock.willie.Willie;
import org.pircbotx.Channel;
import org.pircbotx.Colors;
import org.pircbotx.User;

public class LeaveCommandHandler implements CommandHandler {
    @Override
    public void handle(Willie bot, Channel channel, User sender, String[] args) {
        // Prevent leaving the last channel.
        if(args.length > 0 && !args[0].startsWith("#")) args[0] = "#" + args[0];
        if(args.length == 0 && bot.getChannels().size() == 1) {
            channel.sendMessage(Colors.RED + "Please don't make me leave!");
            return;
        }

        if(args.length > 0 && !bot.getChannelsNames().contains(args[0])) {
            channel.sendMessage(Colors.RED + "Was I supposed to be in there?");
            return;
        }

        if(args.length > 0 && !channel.getName().equals(args[0])) {
            channel.sendMessage(Colors.RED + String.format("Leaving channel %s", args[0]));
        }

        Channel oldChannel = bot.getChannel(args[0]);
        oldChannel.sendMessage(Colors.RED + String.format("%s says I don't belong here...", sender.getNick()));
        bot.partChannel(oldChannel);
        bot.getConfig().removeChannel(oldChannel.getName());
    }
}
