package com.drtshock.willie.commands;

import com.drtshock.willie.Willie;
import com.drtshock.willie.command.Command;
import com.drtshock.willie.command.CommandType;
import com.drtshock.willie.command.ICommand;
import org.pircbotx.Channel;
import org.pircbotx.User;

public class HelpCommand implements ICommand {

    public void handle(Willie bot, Command cmd, CommandType type, Channel channel, User sender, String[] args) {
        bot.send("You can use ! to have the output of a command printed to the channel or | for it to send as a notice to just you.", channel, sender, CommandType.NOTICE_SENDER);
        bot.send("Each channel has its own commands that are arbitrarily defined by ops.", channel, sender, CommandType.NOTICE_SENDER);
        bot.send("Global commands:", channel, sender, CommandType.NOTICE_SENDER);
        for (Command command : bot.getCommandManager().getCommands()) {
            bot.send(command.getHelp(), channel, sender, CommandType.NOTICE_SENDER);
        }
    }
}
