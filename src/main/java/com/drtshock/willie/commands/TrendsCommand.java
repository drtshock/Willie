package com.drtshock.willie.commands;

import org.pircbotx.Channel;
import org.pircbotx.User;

import twitter4j.Trend;
import twitter4j.Trends;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

import com.drtshock.willie.Willie;
import com.drtshock.willie.command.Command;
import com.drtshock.willie.command.CommandType;
import com.drtshock.willie.command.ICommand;

public class TrendsCommand implements ICommand {

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

        try {
        	Trends trend = twitter.getPlaceTrends(1);
			
			StringBuilder sb = new StringBuilder();
	        int trendsToShow = 5;
	        int trendsToShowLeft = 5;
	        
	        for (Trend t : trend.getTrends()) {
	            if (trendsToShow > 0) {
	            	trendsToShowLeft--;
	                
	                if (trendsToShowLeft == 0) {
	                    sb.append(t.getName());
	                } else {
	                    sb.append(t.getName() + ", ");
	                }
	            } else {
	            	bot.send("This command has been configured incorrectly! No trends have been set to be displayed.", channel, sender, CommandType.NOTICE_SENDER);
	            }
	        }
	        
	        if (trendsToShow > 1) {
	        	bot.send("Top " + trendsToShow + " Trends on Twitter right now: " + sb.toString(), channel, sender, CommandType.SEND_CHANNEL);
	        } else {
	        	bot.send("The top Trend on Twitter right now is " + sb.toString(), channel, sender, CommandType.SEND_CHANNEL);
	        }
	    } catch (TwitterException e) {
	    	bot.send("TwitterException occured while executing this command!", channel, sender, CommandType.SEND_CHANNEL);
	    	e.printStackTrace();
		}
    }
}
