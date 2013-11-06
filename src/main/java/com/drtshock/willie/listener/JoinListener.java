package com.drtshock.willie.listener;

import org.pircbotx.Channel;
import org.pircbotx.Colors;
import org.pircbotx.User;
import org.pircbotx.hooks.Listener;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.JoinEvent;

import com.drtshock.willie.Willie;

/**
 * @author drtshock
 */
public class JoinListener extends ListenerAdapter<Willie> implements Listener<Willie> {

    private Willie bot;

    public JoinListener(Willie willie) {
        this.bot = willie;
    }

    @Override
    public void onJoin(JoinEvent<Willie> event) {
        Channel channel = event.getChannel();
        User sender = event.getUser();
        if (!(channel.getVoices().contains(sender) || channel.getOps().contains(sender)) && bot.getConfig().hasJoinMessage(channel)) {
            String message = event.getBot().getConfig().getJoinMessage(channel).replace("{name}", sender.getNick());
            event.getChannel().send().message(Colors.RED + "[AutoMsg] " + Colors.NORMAL + message);
        }
    }
}
