package com.drtshock.willie.logging;

import com.drtshock.willie.Willie;
import org.pircbotx.Channel;
import org.pircbotx.hooks.Listener;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Set;
import java.util.logging.Logger;

public class LogManager extends ListenerAdapter<Willie> implements Listener<Willie> {
    private static final Logger logger = Logger.getLogger(LogManager.class.getName());
    private Willie bot;
    private HashMap<String, ChannelLogger> channelLoggerMap = new HashMap<>();
    private final LogFormatter logFormatter;

    public LogManager(Willie bot, LogFormatter logFormatter) {
        this.bot = bot;
        this.logFormatter = logFormatter;
    }

    public Willie getBot() {
        return bot;
    }

    public Collection<ChannelLogger> getChannelLoggers() {
        return channelLoggerMap.values();
    }

    public Set<String> getLoggedChannelNames() {
        return channelLoggerMap.keySet();
    }

    @Override
    public void onMessage(MessageEvent<Willie> event) {
        if(event.getChannel() == null) return;
        ChannelLogger logger = channelLoggerMap.get(event.getChannel().getName());
        if(logger != null) {
            logger.writeMessage(event.getUser(), event.getMessage());
        }
    }

    @Override
    public void onJoin(JoinEvent<Willie> event) {
        // Create new ChannelLogger when bot joins channel
        if(event.getUser().getNick().equals(event.getBot().getNick())) {
            if(!event.getBot().getConfig().getLogBlacklist().contains(event.getChannel().getName())) {
                if(!channelLoggerMap.containsKey(event.getChannel().getName())) {
                    channelLoggerMap.put(event.getChannel().getName(),
                            // TODO Move ChannelLogger construction to a class that can better accommodate custom loggers
                            new BasicChannelLogger(event.getBot().getServer(),
                                    event.getChannel(),
                                    event.getBot().getConfig().getLoggingDirectory(),
                                    logFormatter));
                    logger.info("Created new logger for channel " + event.getChannel().getName());
                }
            }
        }

        ChannelLogger logger = channelLoggerMap.get(event.getChannel().getName());
        if(logger != null) {
            logger.writeJoin(event.getUser());
        }
    }

    @Override
    public void onPart(PartEvent<Willie> event) {
        // Close logger if the bot leaves the channel.
        if(event.getUser().getNick().equals(event.getBot().getNick())) {
            ChannelLogger logger = channelLoggerMap.get(event.getChannel().getName());
            if(logger != null) {
                logger.close();
                channelLoggerMap.remove(event.getChannel().getName());
                LogManager.logger.info("Closed logger for channel " + event.getChannel().getName());
            }
        }

        else {
            ChannelLogger logger = channelLoggerMap.get(event.getChannel().getName());
            if(logger != null) {
                logger.writePart(event.getUser());
            }
        }
    }

    @Override
    public void onQuit(QuitEvent<Willie> event) {
        for(Channel channel : event.getUser().getChannels()) {
            ChannelLogger logger = channelLoggerMap.get(channel.getName());
            if(logger != null) {
                logger.writeQuit(event.getUser(), event.getReason());
            }
        }
    }

    @Override
    public void onNickChange(NickChangeEvent<Willie> event) {
        for(Channel channel : event.getUser().getChannels()) {
            ChannelLogger logger = channelLoggerMap.get(channel.getName());
            if(logger != null) {
                logger.writeNickChange(event.getUser(), event.getOldNick(), event.getNewNick());
            }
        }
    }
}
