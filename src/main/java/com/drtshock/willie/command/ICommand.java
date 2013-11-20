package com.drtshock.willie.command;

import com.drtshock.willie.Willie;
import org.pircbotx.Channel;
import org.pircbotx.User;

public interface ICommand {

    public void handle(Willie bot, Command cmd, CommandType type, Channel channel, User sender, String[] args);

}
