package com.drtshock.willie.command.utility;

import java.io.IOException;

import org.pircbotx.Channel;
import org.pircbotx.Colors;
import org.pircbotx.User;

import com.drtshock.willie.Willie;
import com.drtshock.willie.command.CommandHandler;
import com.drtshock.willie.util.Dictionary;
import com.drtshock.willie.util.Dictionary.Definition;

public class DefineCommandHandler implements CommandHandler {

    @Override
    public void handle(Willie bot, Channel channel, User sender, String[] args) {
        if(args.length == 0) { // Display dictionary list
            channel.sendMessage("Dictionary List:");
            for(Dictionary dict : Dictionary.values())
                channel.sendMessage(Colors.CYAN + dict.toString() + Colors.NORMAL + " (id = " + Colors.BLUE + dict.getID() + Colors.NORMAL + ")");
        } else if(args.length == 2) {
            Dictionary dict;
            
            try {
                dict = Dictionary.getDictionaryFromID(Integer.parseInt(args[0]));
            } catch(NumberFormatException e) {
                dict = null;
            }
            
            if(dict == null) {
                channel.sendMessage(Colors.RED + "Please enter a valid dictionary id.");
                return;
            }
            
            Definition def;
            try {
                def = dict.getDefinition(args[1].replace('_', ' '));
            } catch (IOException e) {
                def = null;
            }
            
            if(def == null) {
                channel.sendMessage(Colors.RED + "I couldn't not lookup that definition. Sorry.");
                return;
            }
            
            channel.sendMessage("Word: " + Colors.CYAN + args[1]);
            channel.sendMessage("Definition: " + Colors.CYAN + def.getDefinition());
            channel.sendMessage("For a fuller definition visit: " + def.getUrl());
            
        } else { // Display usage
            channel.sendMessage(Colors.RED + "Usage: .define <dictionary id> <word (underscores as spaces)>");
        }
    }

}
