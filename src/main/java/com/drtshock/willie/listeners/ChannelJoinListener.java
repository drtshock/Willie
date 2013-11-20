package com.drtshock.willie.listeners;

import com.drtshock.willie.Willie;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.JoinEvent;

public class ChannelJoinListener extends ListenerAdapter {

    private Willie bot;

    public ChannelJoinListener(Willie bot) {
        this.bot = bot;
    }

    public void onEvent(JoinEvent event) {
        // True if we are the user joining the channel.
        if (event.getUser().getNick().equalsIgnoreCase(bot.getPircBotX().getNick())) {

        } else {

        }

    }
}
