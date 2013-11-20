package com.drtshock.willie;

import com.drtshock.willie.command.ArbitraryCommands;
import com.drtshock.willie.command.Command;
import com.drtshock.willie.command.CommandManager;
import com.drtshock.willie.command.CommandType;
import com.drtshock.willie.commands.HelpCommand;
import com.drtshock.willie.commands.mcstats.GlobalMCStatsCommand;
import com.drtshock.willie.listeners.ChannelJoinListener;
import org.pircbotx.Channel;
import org.pircbotx.Configuration;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

public class Willie {

    private Willie willie;
    private PircBotX bot;
    private ArbitraryCommands arbitraryCommands;
    private CommandManager commandManager;

    public Willie() {
        this.willie = this;
        this.arbitraryCommands = new ArbitraryCommands(this);
        this.commandManager = new CommandManager(this);
        connect();
        registerCommands();
    }

    // new Command("command name", "help message", handler class, admin only)
    private void registerCommands() {
        this.commandManager.registerCommand(new Command("help", "help is on the way!", new HelpCommand(), false));
        this.commandManager.registerCommand(new Command("gstats", "View global stats from mcstats.org", new GlobalMCStatsCommand(), false));
    }

    private void connect() {
        Configuration configuration = new Configuration.Builder()
                .setName("Willie")
                .setLogin("Willie")
                .setAutoNickChange(false)
                .setCapEnabled(true)
                .setServer("drtshock.com", 5555)
                .addAutoJoinChannel("#drtshock")
                .addListener(new ChannelJoinListener(this))
                .buildConfiguration();
        bot = new PircBotX(configuration);
        try {
            bot.startBot(); // connect him to the server.
        } catch (Exception e) {
            System.out.println("Oh no. The bot didn't connect :(");
            e.printStackTrace();
        }

    }

    /**
     * Sends a message. Should be used in all commands.
     *
     * @param message - the message to be sent.
     * @param channel - the channel in which the message will be sent.
     * @param sender  - the sender of the command.
     * @param type    - the CommandType.
     */
    public void send(String message, Channel channel, User sender, CommandType type) {
        switch (type) {
            case NOTICE_SENDER:
                channel.send().message(sender, message);
            case SEND_CHANNEL:
                channel.send().message(message);
        }
    }

    public ArbitraryCommands getArbitraryCommands() {
        return this.arbitraryCommands;
    }

    public CommandManager getCommandManager() {
        return this.commandManager;
    }

    public PircBotX getPircBotX() {
        return this.bot;
    }

    public static void main(String[] args) {
        new Willie();
    }

}
