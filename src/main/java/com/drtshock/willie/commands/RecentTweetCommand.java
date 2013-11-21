package com.drtshock.willie.commands;

import org.pircbotx.Channel;
import org.pircbotx.Colors;
import org.pircbotx.User;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

import com.drtshock.willie.Willie;
import com.drtshock.willie.command.Command;
import com.drtshock.willie.command.CommandType;
import com.drtshock.willie.command.ICommand;

public class RecentTweetCommand implements ICommand {

    public void handle(Willie bot, Command cmd, CommandType type, Channel channel, User sender, String[] args) {
    	ConfigurationBuilder cb = new ConfigurationBuilder();
        /* Needs to be reconfigured when configuration system is done
         * 
         * cb.setDebugEnabled(true)
                .setOAuthConsumerKey(bot.getConfig().getTwitterConsumerKey())
                .setOAuthConsumerSecret(bot.getConfig().getTwitterConsumerKeySecret())
                .setOAuthAccessToken(bot.getConfig().getTwitterAccessToken())
                .setOAuthAccessTokenSecret(bot.getConfig().getTwitterAccessTokenSecret());
        */
        TwitterFactory tf = new TwitterFactory(cb.build());
        Twitter twitter = tf.getInstance();
        String handle = args[0];

        if (!handle.startsWith("@")) {
            handle = "@" + handle;
        }

        try {
            Status status = twitter.showUser(handle).getStatus();
            bot.send("(" + sender.getNick() + ")" + Colors.BOLD + "@" + status.getUser().getScreenName() + ": "
                    + Colors.NORMAL + status.getText(), channel, sender, CommandType.SEND_CHANNEL);
        } catch (TwitterException e) {
            bot.send("(" + sender.getNick() + ") " + Colors.RED + "Failed to retrieve status for " + Colors.BOLD + args[0], channel, sender, CommandType.SEND_CHANNEL);
            e.printStackTrace();
        }
    }
}
