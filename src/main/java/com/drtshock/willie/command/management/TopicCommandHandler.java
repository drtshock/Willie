package com.drtshock.willie.command.management;

import com.drtshock.willie.Willie;
import com.drtshock.willie.command.CommandHandler;
import org.pircbotx.Channel;
import org.pircbotx.User;

/**
 * @author DSH105
 */
public class TopicCommandHandler implements CommandHandler {

    //topic <topic>
    @Override
    public void handle(Willie bot, Channel channel, User sender, String[] args) throws Exception {
        if (channel.getOps().contains(sender)) {
            if (args.length == 0) {
                sender.sendMessage("Usage: !topic <topic>");
            } else if (args.length >= 1) {
                StringBuilder sb = new StringBuilder();
                for (String s : args) {
                    sb.append(s).append(" ");
                }
                String jm = sb.toString().trim();
                bot.setTopic(channel, jm);
                bot.sendAction(channel, "sets topic to \"" + jm + "\"");
                bot.save();
            } else {
                sender.sendMessage("Usage: !topic <topic>");
            }
        } else {
            channel.sendMessage("You are not a channel op");
        }
    }
}