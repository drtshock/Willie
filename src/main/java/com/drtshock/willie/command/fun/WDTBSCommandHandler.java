package com.drtshock.willie.command.fun;

import com.drtshock.willie.Willie;
import com.drtshock.willie.command.CommandHandler;
import com.google.code.chatterbotapi.ChatterBot;
import com.google.code.chatterbotapi.ChatterBotFactory;
import com.google.code.chatterbotapi.ChatterBotSession;
import com.google.code.chatterbotapi.ChatterBotType;

import org.pircbotx.Channel;
import org.pircbotx.Colors;
import org.pircbotx.User;

/**
 * @author stuntguy3000
 */
public class WDTBSCommandHandler implements CommandHandler {

    @Override
    public void handle(Willie bot, Channel channel, User sender, String[] args) {
        if (args.length < 1) {
            channel.sendMessage(Colors.RED + "Please supply a message " + sender.getNick() + "!");
            return;
        }

        try {
            ChatterBotFactory factory = new ChatterBotFactory();
    
            ChatterBot cbot;
            
            cbot = factory.create(ChatterBotType.CLEVERBOT);
            
            ChatterBotSession cBotSession = cbot.createSession();
    
            ChatterBot pbot = factory.create(ChatterBotType.PANDORABOTS, "b0dafd24ee35a477");
            ChatterBotSession pBotSession = pbot.createSession();
    
            StringBuilder question = new StringBuilder();
            for (String arg : args) {
                question.append(arg + " ");
            }
    
            String cBotResponce = cBotSession.think(question.toString());
            String pBotResponce = pBotSession.think(question.toString());
    
            channel.sendMessage(Colors.BROWN + "What does the bot say?");
            channel.sendMessage(Colors.CYAN + "Cleverbot: " + Colors.DARK_GREEN + cBotResponce);
            channel.sendMessage(Colors.CYAN + "Pandorabot: " + Colors.DARK_GREEN + pBotResponce);
        
        } catch (Exception e) {
            channel.sendMessage(Colors.RED + "An error has occured: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
