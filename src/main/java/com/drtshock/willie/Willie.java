package com.drtshock.willie;

import org.pircbotx.Channel;
import org.pircbotx.Configuration;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

import com.drtshock.willie.command.ArbitraryCommands;
import com.drtshock.willie.command.Command;
import com.drtshock.willie.command.CommandManager;
import com.drtshock.willie.command.CommandType;
import com.drtshock.willie.commands.HelpCommand;
import com.drtshock.willie.commands.RecentTweetCommand;
import com.drtshock.willie.commands.TrendsCommand;
import com.drtshock.willie.commands.mcstats.GlobalMCStatsCommand;
import com.drtshock.willie.commands.mcstats.MCStatsCommand;
import com.drtshock.willie.commands.misc.ShortenCommand;
import com.drtshock.willie.commands.plugins.LatestFileCommand;
import com.drtshock.willie.commands.plugins.PluginCommand;
import com.drtshock.willie.configuration.WillieConfiguration;
import com.drtshock.willie.listeners.ChannelJoinListener;

public class Willie {

    private Willie willie;
    private PircBotX bot;
    private ArbitraryCommands arbitraryCommands;
    private CommandManager commandManager;
    private WillieConfiguration mainConfig;

    public Willie() {
        this.willie = this;
        this.arbitraryCommands = new ArbitraryCommands(this);
        this.commandManager = new CommandManager(this);
        connect();
        registerCommands();
        mainConfig = new WillieConfiguration("willie.yml"); // create the main config.
    }

    /**
     * Gets the main config of the bot.
     *
     * @return the main config of the bot.
     */
    public WillieConfiguration getMainConfig() {
        return mainConfig;
    }

    /**
     * Creates a new configuration for a channel.
     *
     * @param channel - the channel's instead of the config.
     */
    public void createConfig(Channel channel) {
        new WillieConfiguration(channel);
    }

    // new Command("command name", "help message", handler class, admin only)
    private void registerCommands() {
        this.commandManager.registerCommand(new Command("help", "help is on the way!", new HelpCommand(), false));
        this.commandManager.registerCommand(new Command("gstats", "View global stats from mcstats.org", new GlobalMCStatsCommand(), false));
        this.commandManager.registerCommand(new Command("stats", "View a plugin's information on MCStats.org", new MCStatsCommand(), false));
        this.commandManager.registerCommand(new Command("plugin", "Get some info with !plugin <info>", new PluginCommand(), false));
        this.commandManager.registerCommand(new Command("latest", "Get the latest file with !latest <plugin>", new LatestFileCommand(), false));
        this.commandManager.registerCommand(new Command("shorten", "Shorten a URL with !shorten <url>", new ShortenCommand(), false));
        this.commandManager.registerCommand(new Command("rtweet", "Get the latest Tweet from any Twitter handle", new RecentTweetCommand(), false));
        this.commandManager.registerCommand(new Command("trends", "See what is Trending on Twitter!", new TrendsCommand(), false));
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
