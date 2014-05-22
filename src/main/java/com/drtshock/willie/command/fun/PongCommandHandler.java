package com.drtshock.willie.command.fun;

import com.drtshock.willie.Willie;
import com.drtshock.willie.command.CommandHandler;
import org.pircbotx.Channel;
import org.pircbotx.User;

import java.util.ArrayList;
import java.util.List;

/**
 * @author stuntguy3000
 */
public class PongCommandHandler implements CommandHandler {
    private List<String> messages;

    public PongCommandHandler() {
        this.messages = new ArrayList<>();

        this.messages.add("Come at me {u}! I will smash you at ping-pong");
        this.messages.add("{u} can't play ping-pong");
        this.messages.add("Who wants to challenge {u} to a game of ping-pong?");
        this.messages.add("Taking all bets - Willie vs {u} at ping-pong. First to 11.");
        this.messages.add("{u} has uber ping-pong hacks. Like a bionic hand or something...");
        this.messages.add("Who wants to pong {u} with me?");
        this.messages.add("Grab your bats is time to play ping-pong! Come at me {u}");
    }

    @Override
    public void handle(Willie bot, Channel channel, User sender, String[] args) {
        channel.sendMessage(this.messages.get(rand.nextInt(this.messages.size())).replace("{u}", sender.getNick()));
    }
}