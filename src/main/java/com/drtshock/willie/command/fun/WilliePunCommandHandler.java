package com.drtshock.willie.command.fun;

import java.util.ArrayList;

import org.pircbotx.Channel;
import org.pircbotx.User;

import com.drtshock.willie.Willie;
import com.drtshock.willie.command.CommandHandler;

public class WilliePunCommandHandler implements CommandHandler {
	
	private ArrayList<String> msgs;

    public WilliePunCommandHandler() {
        this.msgs = new ArrayList<String>();

        this.msgs.add("Did you hear about the guy whose whole left side was cut off? He's all right now.");
        this.msgs.add("I wondered why the baseball was getting bigger. Then it hit me");
        this.msgs.add("I'm reading a book about anti-gravity. It's impossible to put down.");
        this.msgs.add("It's not that the man did not know how to juggle, he just didn't have the balls to do it.");
        this.msgs.add("I'm glad I know sign language, it's pretty handy.");
        this.msgs.add("I couldn't quite remember how to throw a boomerang, but eventually it came back to me.");
        this.msgs.add("The other day I held the door open for a clown. I thought it was a nice jester.");
        this.msgs.add("Did you hear about the guy who got hit in the head with a can of soda? He was lucky it was a soft drink.");
    }

    @Override
    public void handle(Willie bot, Channel channel, User sender, String[] args) {
        channel.sendMessage(msgs.get(rand.nextInt(msgs.size())));
    }

}
