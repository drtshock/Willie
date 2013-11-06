package com.drtshock.willie.command.utility;

import com.drtshock.willie.Willie;
import com.drtshock.willie.command.CommandHandler;
import com.drtshock.willie.util.WebHelper;
import org.pircbotx.Channel;
import org.pircbotx.Colors;
import org.pircbotx.User;

/**
 * @author drtshock
 */
public class ShortenCommandHandler implements CommandHandler {

    @Override
    public void handle(Willie bot, Channel channel, User sender, String[] args) {
        if (args.length == 1) {
            String url = WebHelper.shortenURL(args[0]);
            if ("".equals(url)) {
                channel.send().message(Colors.RED + "Sorry but I couldn't shorten that for you.");
            } else {
                channel.send().message("Here ya go: " + url);
            }
        } else {
            channel.send().message("!shorten <url>");
        }
    }
}
