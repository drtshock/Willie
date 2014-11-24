package com.drtshock.willie;

import com.drtshock.willie.config.WillieConfig;
import com.drtshock.willie.config.WillieConfigOption;
import com.drtshock.willie.util.FileUtil;
import org.kitteh.irc.Bot;
import org.kitteh.irc.BotBuilder;

import java.net.URISyntaxException;
import java.util.ArrayList;

/**
 * A configurable IRC bot.
 */
public class Willie {
    /**
     * The bot that handles connections and interfacing with the IRC server.
     */
    private final Bot bot;

    /**
     * The configuration options in the form of an Object.
     */
    private final WillieConfig config;

    /**
     * Instantiate Willie with default options.
     */
    public Willie() {
        this(new WillieConfig());
    }

    /**
     * Instantiate Willie with specified options.
     *
     * @param config config object containing specified options
     */
    public Willie(WillieConfig config) {
        this.config = config;
        // TODO: check that config has bound values to correct type, or account for errors if not
        BotBuilder botBuilder = new BotBuilder();
        if (config.getOptionValue(WillieConfigOption.BIND_HOST) != null) {
            botBuilder.bind((String) config.getOptionValue(WillieConfigOption.BIND_HOST));
            botBuilder.bind((Integer) config.getOptionValue(WillieConfigOption.BIND_PORT));
        }
        botBuilder.server((String) config.getOptionValue(WillieConfigOption.SERVER_HOST));
        botBuilder.server((Integer) config.getOptionValue(WillieConfigOption.SERVER_PORT));
        if (config.getOptionValue(WillieConfigOption.SERVER_PASSWORD) != null) {
            botBuilder.serverPassword((String) config.getOptionValue(WillieConfigOption.SERVER_PASSWORD));
        }
        botBuilder.name((String) config.getOptionValue(WillieConfigOption.NAME));
        botBuilder.nick((String) config.getOptionValue(WillieConfigOption.NICK));
        botBuilder.realName((String) config.getOptionValue(WillieConfigOption.REAL_NAME));
        botBuilder.user((String) config.getOptionValue(WillieConfigOption.USER));
        this.bot = botBuilder.build();
        for (String channel : (ArrayList<String>) config.getOptionValue(WillieConfigOption.DEFAULT_CHANNELS)) {
            this.bot.addChannel(channel.toLowerCase());
        }
    }

    /**
     * Gets the config holding the options being used by Willie.
     *
     * @return the WillieConfig object holding the options being used by Willie
     */
    public WillieConfig getConfig() {
        return this.config;
    }

    public static void main(String[] args) {
        WillieConfig config = null;
        try {
            config = WillieConfig.fromYamlDirectory(FileUtil.getWillieDirectory());
        } catch (URISyntaxException e) {
            // TODO: raise a fuss
            e.printStackTrace();
        }
        Willie willie = new Willie(config);
    }
}