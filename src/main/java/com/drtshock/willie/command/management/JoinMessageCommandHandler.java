package com.drtshock.willie.command.management;

import com.drtshock.willie.Willie;
import com.drtshock.willie.command.CommandHandler;
import org.pircbotx.Channel;
import org.pircbotx.User;

/**
 * @author drtshock
 */
public class JoinMessageCommandHandler implements CommandHandler {

    // joinmsg <delete>
    // joinmsg ...
    @Override
    public void handle(Willie bot, Channel channel, User sender, String[] args) throws Exception {
        if (channel.getOps().contains(sender)) {
            if (args.length == 1 && args[0].equalsIgnoreCase("delete")) {
                if (bot.getConfig().hasJoinMessage(channel)) {
                    channel.send().message("Join message removed.");
                    bot.getConfig().setJoinMessage(channel, "");
                    bot.save();
                } else {
                    channel.send().message("This channel has no join message");
                }
            } else if (args.length > 0) {
                StringBuilder sb = new StringBuilder();
                for (String s : args) {
                    sb.append(s).append(" ");
                }
                String jm = sb.toString().trim();
                bot.getConfig().setJoinMessage(channel, jm);
                bot.sendAction(channel, "sets join message to \"" + jm + "\"");
                bot.save();
            } else {
                sender.send().message("Usage: /joinmsg <delete | ...>");
            }
        } else {
            channel.send().message("You are not a channel op");
        }
    }
}
