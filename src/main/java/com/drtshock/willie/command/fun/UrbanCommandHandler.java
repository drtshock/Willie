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
        if(args.length != 1) {
            channel.sendMessage(Colors.RED + "Usage: .urban <word (underscores as spaces)>");
        } else {
            Dictionary.Definition def;
            try {
                def = Dictionary.URBAN_DICTIONARY.getDefinition(args[1].replace('_', ' '));
            } catch (IOException e) {
                def = null;
            }

            if(def == null) {
                channel.sendMessage(Colors.RED + "I couldn't not lookup that definition. Sorry.");
                return;
            }

            channel.sendMessage("Word: " + Colors.CYAN + args[1]);
            channel.sendMessage("Definition: " + Colors.CYAN + def.getDefinition());
            channel.sendMessage("For a full definition visit: " + def.getUrl());
        }
    }

}
