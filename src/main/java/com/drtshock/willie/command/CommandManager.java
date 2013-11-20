package com.drtshock.willie.command;

import com.drtshock.willie.Willie;
import org.pircbotx.Channel;
import org.pircbotx.User;
import org.pircbotx.hooks.events.MessageEvent;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class CommandManager {

    private Willie bot;
    private Map<String, Command> commands;

    public CommandManager(Willie bot) {
        this.bot = bot;
        this.commands = new LinkedHashMap<>();
    }

    public void registerCommand(Command command) {
        this.commands.put(command.getName(), command);
    }

    public Collection<Command> getCommands() {
        return this.commands.values();
    }

    public void onMessage(MessageEvent event) {
        final String message = event.getMessage();
        final Channel channel = event.getChannel();
        final User sender = event.getUser();

        if (message.toLowerCase().endsWith("o/") && (!message.contains("\\o/"))) {
            channel.send().message("\\o");
            return;
        }
        if (message.toLowerCase().endsWith("\\o") && (!message.contains("\\o/"))) {
            channel.send().message("o/");
            return;
        }

        String prefix = message.substring(0, 1);
        CommandType type;

        if (prefix.equalsIgnoreCase("!")) {
            type = CommandType.SEND_CHANNEL;
        } else if (prefix.equalsIgnoreCase("|")) {
            type = CommandType.NOTICE_SENDER;
        } else {
            return;
        }

        String[] parts = message.substring(1).split(" ");
        String[] args = new String[parts.length - 1];
        System.arraycopy(parts, 1, args, 0, args.length);

        String commandName = parts[0].toLowerCase();

        if (this.commands.containsKey(commandName)) {
            Command command = this.commands.get(commandName);
            if (command.isAdminOnly()) {
                bot.send("You are not an admin.", channel, sender, CommandType.NOTICE_SENDER);
            } else {
                try {
                    command.getHandler().handle(bot, command, type, channel, sender, args);
                } catch (Exception e) {
                    e.printStackTrace();
                    bot.send("Failed to execute command " + commandName, channel, sender, CommandType.SEND_CHANNEL);
                }
            }
        } else if (bot.getArbitraryCommands().isArbitraryCommand(channel, commandName)) {
            executeArbitraryCommand(commandName, channel, sender, type);
        }
    }

    private void executeArbitraryCommand(String command, Channel channel, User sender, CommandType type) {

    }

}
