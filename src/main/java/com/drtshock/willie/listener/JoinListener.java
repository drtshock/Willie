package com.drtshock.willie.listener;

import com.drtshock.willie.Willie;
import org.pircbotx.Channel;
import org.pircbotx.Colors;
import org.pircbotx.User;
import org.pircbotx.hooks.Listener;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.JoinEvent;
import org.pircbotx.hooks.events.MessageEvent;

import java.util.HashMap;
import java.util.regex.Pattern;

/**
 * @author drtshock
 */
public class JoinListener extends ListenerAdapter<Willie> implements Listener<Willie> {

    private Willie bot;
    private HashMap<String, String> users = new HashMap<>();
    private final String URL_REGEX = "\\b\\d{1,3}+\\p{P}*\\d{1,3}+\\p{P}*\\d{1,3}+\\p{P}*\\d{1,3}+\\b|([\\w-\\.]+)((?:[\\w]+\\.)+)([a-zA-Z]{2,4})";
    private Pattern pattern = Pattern.compile(URL_REGEX);

    public JoinListener(Willie willie) {
        this.bot = willie;
    }

    @Override
    public void onJoin(JoinEvent<Willie> event) {
        Channel channel = event.getChannel();
        User sender = event.getUser();
        users.put(sender.getRealName(), channel.getName());
        if (!(channel.getVoices().contains(sender) || channel.getOps().contains(sender)) && bot.getConfig().hasJoinMessage(channel)) {
            String message = event.getBot().getConfig().getJoinMessage(channel).replace("{name}", sender.getNick());
            event.getChannel().sendMessage(Colors.RED + "[AutoMsg] " + Colors.NORMAL + message);
        }
    }

    @Override
    public void onMessage(MessageEvent<Willie> event) {
        Channel channel = event.getChannel();
        User sender = event.getUser();
        if (users.containsKey(sender.getRealName()) && users.get(sender.getRealName()).equalsIgnoreCase(channel.getName()) && (!channel.getVoices().contains(sender) && !channel.getOps().contains(sender))) {
            if (pattern.matcher(event.getMessage()).find()) {
                Willie.getInstance().ban(channel, sender.getHostmask());
                Willie.getInstance().kick(channel, sender, "Joining and posting links is not permitted.");
            } else if (sender.getRealName().equalsIgnoreCase("JoPhEsTuS")) {
                Willie.getInstance().ban(channel, sender.getHostmask());
                Willie.getInstance().kick(channel, sender, "Joining and posting links is not permitted.");
            } else {
                users.remove(sender.getRealName());
            }
        }
    }
}
