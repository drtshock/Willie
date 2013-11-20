package com.drtshock.willie.commands.misc;

import com.drtshock.willie.Willie;
import com.drtshock.willie.command.Command;
import com.drtshock.willie.command.CommandType;
import com.drtshock.willie.command.ICommand;
import com.drtshock.willie.util.WebHelper;
import org.pircbotx.Channel;
import org.pircbotx.User;

public class ShortenCommand implements ICommand {

    public void handle(Willie bot, Command cmd, CommandType type, Channel channel, User sender, String[] args) {
        if (args.length == 1) {
            String url = WebHelper.getShortenedURL(args[0]);
            if ("".equals(url)) {
                bot.send("Sorry but I couldn't shorten that for you.", channel, sender, type);
            } else {
                bot.send("Here ya go: " + url.toString(), channel, sender, type);
            }
        } else {
            bot.send(cmd.getHelp(), channel, sender, type);
        }
    }
}