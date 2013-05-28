package com.drtshock.willie.command;

import com.drtshock.willie.Willie;
import org.pircbotx.Channel;
import org.pircbotx.User;

public class HelpCommandHandler implements CommandHandler {

    @Override
    public void handle(Willie bot, Channel channel, User sender, String[] args) {
        String cmdPrefix = bot.getConfig().getCommandPrefix();
        for (Command command : bot.commandManager.getCommands()) {
            sender.sendMessage(cmdPrefix + command.getName() + " - " + command.getHelp());
        }
    }

}
