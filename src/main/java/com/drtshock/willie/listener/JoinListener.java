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
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author drtshock
 */
public class JoinListener extends ListenerAdapter<Willie> implements Listener<Willie> {

    private Willie bot;
    private Map<String, String> users = new HashMap<>();
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
        if (!exempt(sender, channel) && Willie.getInstance().getConfig().isBlacklisted(event.getMessage())) {
            kick(sender, channel, "Please do not spam.");
        }
        /*if (users.containsKey(sender.getRealName()) && users.get(sender.getRealName()).equalsIgnoreCase(channel.getName()) && (!channel.getVoices().contains(sender) && !channel.getOps().contains(sender))) {
            if (pattern.matcher(event.getMessage()).find()) {
                kick(sender, channel, "Joining and posting links is not permitted.");
            } else {
                users.remove(sender.getRealName());
            }
        }*/
    }

    private boolean exempt(User user, Channel channel) {
        return channel.getOps().contains(user) || channel.getVoices().contains(user);
    }

    private void kick(User user, Channel channel, String reason) {
        Willie.getInstance().kick(channel, user, reason);
    }

    private void kickban(User user, Channel channel, String reason) {
        Willie.getInstance().ban(channel, user.getHostmask());
        Willie.getInstance().kick(channel, user, reason);
    }
}
