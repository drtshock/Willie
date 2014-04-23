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

            ChatterBot cbot = factory.create(ChatterBotType.CLEVERBOT);
            ChatterBotSession cBotSession = cbot.createSession();

            StringBuilder question = new StringBuilder();
            for (String arg : args) {
                question.append(arg).append(" ");
            }

            String cBotResponce = cBotSession.think(question.toString().trim()).trim();

            channel.sendMessage(Colors.CYAN + "Cleverbot's responce: " + Colors.DARK_GREEN + cBotResponce);

        } catch (Exception e) {
            e.printStackTrace();
            channel.sendMessage(Colors.RED + "Error Occured! " + e.getMessage());
        }
    }
}
