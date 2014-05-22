package com.drtshock.willie.command.twitter;

import com.drtshock.willie.Willie;
import com.drtshock.willie.command.CommandHandler;
import org.pircbotx.Channel;
import org.pircbotx.Colors;
import org.pircbotx.User;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

public class TrendsCommandHandler implements CommandHandler {

    @Override
    public void handle(Willie bot, Channel channel, User sender, String[] args) {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(bot.getConfig().getTwitterConsumerKey())
                .setOAuthConsumerSecret(bot.getConfig().getTwitterConsumerKeySecret())
                .setOAuthAccessToken(bot.getConfig().getTwitterAccessToken())
                .setOAuthAccessTokenSecret(bot.getConfig().getTwitterAccessTokenSecret());
        TwitterFactory tf = new TwitterFactory(cb.build());
        Twitter twitter = tf.getInstance();

        try {
            Trends trend = twitter.getPlaceTrends(1);
            StringBuilder sb = new StringBuilder();
            int trendsToShow = 5;

            for (Trend t : trend.getTrends()) {
                if (trendsToShow > 0) {
                    trendsToShow--;

                    if (trendsToShow == 0) {
                        sb.append(t.getName());
                    } else {
                        sb.append(t.getName());
                        sb.append(", ");
                    }
                }
            }

            channel.sendMessage(Colors.NORMAL + "Top " + trendsToShow + " trend(s) on Twitter right now: " + sb.toString().trim());
        } catch (TwitterException e) {
            e.printStackTrace();
        }
    }
}
