package com.drtshock.willie.command;

import com.drtshock.willie.Willie;
import org.pircbotx.Channel;

import java.util.LinkedHashMap;
import java.util.Map;

public class ArbitraryCommands {

    private Willie bot;
    private Map<Channel, String> commands;

    public ArbitraryCommands(Willie bot) {
        this.bot = bot;
        this.commands = new LinkedHashMap<>();
    }

    public boolean isArbitraryCommand(Channel channel, String command) {
        // TODO: All this stuff.
        return false;
    }


}
