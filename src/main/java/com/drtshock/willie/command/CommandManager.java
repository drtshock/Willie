package com.drtshock.willie.command;

import com.drtshock.willie.Willie;
import com.drtshock.willie.auth.Auth;
import com.drtshock.willie.command.misc.stupidexception.AbstractStupidException;
import com.drtshock.willie.github.GistHelper;
import org.pircbotx.Channel;
import org.pircbotx.Colors;
import org.pircbotx.User;
import org.pircbotx.hooks.Listener;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CommandManager extends ListenerAdapter<Willie> implements Listener<Willie> {

    private static final Logger logger = Logger.getLogger(CommandManager.class.getName());
    private Willie bot;
    private Map<String, Command> commands;
    private String cmdPrefix;

    public CommandManager(Willie bot) {
        this.bot = bot;
        this.cmdPrefix = bot.getConfig().getCommandPrefix();
        this.commands = new LinkedHashMap<>();
    }

    public void registerCommand(Command command) {
        this.commands.put(command.getName(), command);
    }

    public Collection<Command> getCommands() {
        return this.commands.values();
    }

    public void setCommandPrefix(String prefix) {
        this.cmdPrefix = prefix;
    }

    @Override
    public void onMessage(MessageEvent<Willie> event) {
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

        if (!message.startsWith(cmdPrefix)) {
            return;
        }

        String[] parts = message.substring(1).split(" ");

        String commandName = parts[0].toLowerCase();
        String[] args = new String[parts.length - 1];
        System.arraycopy(parts, 1, args, 0, args.length);

        Command command = this.commands.get(commandName);
        if (command.isAdminOnly() && !Auth.checkAuth(sender).isAdmin) {
            channel.send().message(Colors.RED + String.format("%s, you aren't an admin. Maybe you forgot to identify yourself?", sender.getNick()));
            return;
        }
        try {
            command.getHandler().handle(this.bot, channel, sender, args);
        } catch (Exception e) {
            final Writer writer = new StringWriter();
            final PrintWriter printWriter = new PrintWriter(writer);
            e.printStackTrace(printWriter);
            final String stackTrace = writer.toString();

            logger.log(Level.SEVERE, e.getMessage(), e);

            final String msg1 = e.getClass().getSimpleName() + " caught when " + sender.getNick() + " used the command \"" + message + "\".";
            channel.send().message(Colors.RED + msg1);
            logger.severe(msg1);

            if (!(e instanceof AbstractStupidException)) {
                final String msg2 = "Got a stacktrace for you, human: " + GistHelper.gist(stackTrace);
                channel.send().message(Colors.RED + msg2);
                logger.severe(msg2);
            } else {
                final String msg2 = "I will not paste this, human.";
                channel.send().message(Colors.RED + msg2);
            }
        }
    }
}
