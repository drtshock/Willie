package com.drtshock.willie.feature;

import com.drtshock.willie.Willie;
import org.kitteh.irc.EventHandler;
import org.kitteh.irc.event.channel.ChannelMessageEvent;
import org.kitteh.irc.event.channel.ChannelPartEvent;

import java.util.ArrayList;
import java.util.HashMap;

public class JenkinsEasterEgg {
    public static final String FEATURE_NAME = "jenkins-easter-egg";
    public static final String EASTER_EGG_MESSAGE = "Oops, looks like Jenkins is going down!";

    public static final int MESSAGE_AMOUNT = 5;
    public static final long MESSAGE_TIME_SPAM = 5000; // 5 seconds

    // TODO: implement random chance

    private final HashMap<String, ArrayList<Long>> lastChannelMessageTimes = new HashMap<>();
    private final Willie willie;

    public JenkinsEasterEgg(Willie willie) {
        this.willie = willie;
    }

    @EventHandler
    public void onChannelMessage(ChannelMessageEvent channelMessageEvent) {
        String channelName = channelMessageEvent.getChannel().getName();
        if (willie.isModuleEnabled(channelName, FEATURE_NAME)) {
            ArrayList<Long> lastMessageTimes = lastChannelMessageTimes.get(channelName);

            if (lastMessageTimes == null) {
                lastMessageTimes = new ArrayList<>();
            }

            long currentTimeMillis = System.currentTimeMillis();
            lastMessageTimes.add(currentTimeMillis);

            if (lastMessageTimes.size() >= MESSAGE_AMOUNT) {
                long firstTime = lastMessageTimes.get(0);

                if (currentTimeMillis - firstTime <= MESSAGE_TIME_SPAM) {
                    this.willie.getBot().sendMessage(channelName, EASTER_EGG_MESSAGE);
                    lastMessageTimes = new ArrayList<>();
                } else {
                    lastMessageTimes = new ArrayList<>(lastMessageTimes.subList(lastMessageTimes.size() - MESSAGE_AMOUNT, lastMessageTimes.size()));
                }
            }

            lastChannelMessageTimes.put(channelName, lastMessageTimes);
        }
    }

    @EventHandler
    public void onChannelPart(ChannelPartEvent channelPartEvent) {
        lastChannelMessageTimes.remove(channelPartEvent.getChannel().getName());
    }
}
