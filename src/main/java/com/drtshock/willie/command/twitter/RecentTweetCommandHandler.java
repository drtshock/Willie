package com.drtshock.willie.command.twitter;

import com.drtshock.willie.Willie;
import com.drtshock.willie.command.CommandHandler;
import org.pircbotx.Channel;
import org.pircbotx.Colors;
import org.pircbotx.User;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

public class RecentTweetCommandHandler implements CommandHandler {

    @Override
    public void handle(Willie bot, Channel channel, User sender, String[] args) {
        Twitter twitter = new TwitterFactory().getInstance();
        try {
            Status status = twitter.showUser(args[0]).getStatus();
            channel.sendMessage("(" + sender.getNick() + ") " + Colors.BOLD + "@" + status.getUser().getScreenName() + ": "
                    + Colors.NORMAL + status.getText());
        } catch (TwitterException e) {
            channel.sendMessage("(" + sender.getNick() + ") " + Colors.RED + "Failed to retrieve status for " + Colors.BOLD + args[0]);
        }

    }
}
