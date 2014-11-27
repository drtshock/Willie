package com.drtshock.willie.feature;

import com.drtshock.willie.Willie;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.kitteh.irc.EventHandler;
import org.kitteh.irc.event.channel.ChannelMessageEvent;

import java.io.IOException;

/**
 * Created by Nick Parks on 11/26/2014 at 3:05 PM.
 */
public class UrlHeader {
    public static final String FEATURE_NAME = "urlheader-feature";

    private final Willie willie;

    public UrlHeader(Willie willie) {
        this.willie = willie;
    }

    @EventHandler
    public void onChannelMessage(ChannelMessageEvent channelMessageEvent) {
        String channelName = channelMessageEvent.getChannel().getName();
        if (willie.isModuleEnabled(channelName, FEATURE_NAME)) {
            String message = channelMessageEvent.getMessage();
            if (!message.startsWith("http")) {
                return;
            }
            try {
                String title = getTitle(message);
                if (title != null) {
                    willie.getBot().sendMessage(channelName, title);
                }
            } catch (IOException e) {
                //Do nothing cause not valid url!
            }
        }
    }

    private String getTitle(String URL) throws IOException {
        Document doc = Jsoup.connect(URL).get();
        return doc.title();
    }
}
