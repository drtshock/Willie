package com.drtshock.willie.command;

import org.pircbotx.Channel;
import org.pircbotx.User;

import com.drtshock.willie.Willie;

public class KickCommandHandler implements CommandHandler {

    @Override
    public void handle(Willie bot, Channel channel, User sender, String[] args) {
        if(args.length != 1) {
            if(sender.getChannelsVoiceIn().contains(channel)) {
                if(channel.getUsers().contains(bot.getUser(args[0]))) {
                    bot.kick(channel, bot.getUser(args[0]));
                } else {
                    bot.sendNotice(sender, "That user is not in the channel!");
                }
            } else {
                bot.sendNotice(sender, "You do not have permission to do that!");
            }
        } else {
            bot.sendNotice(sender, "Usage: !kick <user>");
        }
    }

}
