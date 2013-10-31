package com.drtshock.willie.command.admin;

import org.pircbotx.Channel;
import org.pircbotx.Colors;
import org.pircbotx.User;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationContext;
import twitter4j.http.AccessToken;
import twitter4j.http.OAuthAuthorization;

import com.drtshock.willie.Willie;
import com.drtshock.willie.command.CommandHandler;

/** @author stuntguy3000 */
public class WTweetCommandHandler implements CommandHandler {

    @Override
    public void handle(Willie bot, Channel channel, User sender, String[] args) {
        if (args.length == 0) {
        	channel.sendMessage(Colors.RED + "Please provide a message " + sender.getNick() + "! !wtweet <message>");
        } else {
        	String ACCESS_TOKEN = bot.getConfig().getTwitterAccessToken();
            String ACCESS_TOKEN_SECRET = bot.getConfig().getTwitterAccessTokenSecret();
            String CONSUMER_KEY = bot.getConfig().getTwitterConsumerKey();
            String CONSUMER_SECRET = bot.getConfig().getTwitterConsumerKeySecret();
            String tweet = args[0];
            
            AccessToken accessToken = new AccessToken(ACCESS_TOKEN, ACCESS_TOKEN_SECRET);
            OAuthAuthorization authorization = new OAuthAuthorization(ConfigurationContext.getInstance(), CONSUMER_KEY, CONSUMER_SECRET, accessToken);
            Twitter twitter = new TwitterFactory().getInstance(authorization);
            try {
                twitter.updateStatus(tweet);
                channel.sendMessage(Colors.GREEN + sender.getNick() + " your message was tweeted!");
            } catch (TwitterException e) {
                e.printStackTrace();
                channel.sendMessage(Colors.RED + "Error occurred while updating tweeting!");
            }
        }
        
        channel.sendMessage(Colors.CYAN + "Check out Willie on Twitter! https://twitter.com/WillieIRC");
        
    }
}
