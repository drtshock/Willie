package com.drtshock.willie.command.utility;

import com.drtshock.willie.Willie;
import com.drtshock.willie.command.CommandHandler;
import com.drtshock.willie.util.Dictionary;
import com.drtshock.willie.util.Dictionary.Definition;
import org.pircbotx.Channel;
import org.pircbotx.Colors;
import org.pircbotx.User;

import java.io.IOException;

public class DefineCommandHandler implements CommandHandler {

    @Override
    public void handle(Willie bot, Channel channel, User sender, String[] args) {
        if(args.length != 1) {
            channel.sendMessage(Colors.RED + "Usage: .define <word (underscores as spaces)>");
        } else {
            Definition def;
            try {
                def = Dictionary.DUCK_DUCK_GO.getDefinition(args[1].replace('_', ' '));
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
