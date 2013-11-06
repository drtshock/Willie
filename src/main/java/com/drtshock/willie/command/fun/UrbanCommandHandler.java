package com.drtshock.willie.command.fun;

import com.drtshock.willie.Willie;
import com.drtshock.willie.command.CommandHandler;
import com.drtshock.willie.util.Dictionary;
import org.pircbotx.Channel;
import org.pircbotx.Colors;
import org.pircbotx.User;

import java.io.IOException;

public class UrbanCommandHandler implements CommandHandler {

    @Override
    public void handle(Willie bot, Channel channel, User sender, String[] args) {
        if (args.length == 0) {
            channel.send().message(Colors.RED + "Usage: !urban <word|phrase>");
        } else {
            StringBuilder sb = new StringBuilder();
            for (String arg : args) {
                sb.append(arg).append("+");
            }
            String query = sb.toString();
            query = query.substring(0, query.length() - 1);
            Dictionary.Definition def;
            try {
                def = Dictionary.URBAN_DICTIONARY.getDefinition(query);
            } catch (IOException e) {
                def = null;
            }

            if (def == null) {
                channel.send().message(Colors.RED + "I couldn't lookup that definition. D:");
                return;
            }

            channel.send().message("Word: " + Colors.BLUE + query.replace('+', ' '));
            channel.send().message("Definition: " + def.getDefinition());
            channel.send().message("For a full definition visit: " + def.getUrl());
        }
    }
}
