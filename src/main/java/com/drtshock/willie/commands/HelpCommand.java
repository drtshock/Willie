package com.drtshock.willie.commands;

import com.drtshock.willie.Willie;
import com.drtshock.willie.command.Command;
import com.drtshock.willie.command.CommandType;
import com.drtshock.willie.command.ICommand;
import org.pircbotx.Channel;
import org.pircbotx.User;

public class HelpCommand implements ICommand {

    public void handle(Willie bot, Command cmd, CommandType type, Channel channel, User sender, String[] args) {
        for (Command command : bot.getCommandManager().getCommands()) {
            bot.send(command.getHelp(), channel, sender, CommandType.NOTICE_SENDER);
        }
    }
}
