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
        	String ACCESS_TOKEN = "2166071346-IW8eiiYn8ihnJq8nxSkQ2t6Q1IZwfxXM76t5Q4V";
            String ACCESS_TOKEN_SECRET = "TycBrtKSoZDXdWSvrYwJTmdS6GKcpQj1y2X34crO5roS7";
            String CONSUMER_KEY = "FtuTrGYkLEO9GEP6lQ8ipA";
            String CONSUMER_SECRET = "fk8TxLnllu2QSuW4eIoOyq2P7OKjRdM1ovjDiqoKwA";
            String tweet = args[0];
            
            AccessToken accessToken = new AccessToken(ACCESS_TOKEN, ACCESS_TOKEN_SECRET);
            OAuthAuthorization authorization = new OAuthAuthorization(ConfigurationContext.getInstance(), CONSUMER_KEY, CONSUMER_SECRET, accessToken);
            Twitter twitter = new TwitterFactory().getInstance(authorization);
            try {
                twitter.updateStatus(tweet);
                channel.sendMessage(Colors.GREEN + sender.getNick() + " your message was tweeted!");
            } catch (TwitterException e) {
                System.err.println("Error occurred while updating the status!");
                channel.sendMessage(Colors.RED + "Error occurred while updating tweeting!");
            }
        }
        
        channel.sendMessage(Colors.CYAN + "Check out Willie on Twitter! https://twitter.com/WillieIRC");
        
    }
}
