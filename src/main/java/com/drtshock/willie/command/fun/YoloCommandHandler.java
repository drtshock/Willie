package com.drtshock.willie.command.fun;

import com.drtshock.willie.Willie;
import com.drtshock.willie.command.CommandHandler;
import org.pircbotx.Channel;
import org.pircbotx.User;

import java.util.ArrayList;
import java.util.List;

/**
 * @author KingFaris10
 */
public class YoloCommandHandler implements CommandHandler {
    private List<String> messages;

    public YoloCommandHandler() {
        this.messages = new ArrayList<>();

        this.messages.add("%s only lives once.");
        this.messages.add("You only live once!");
        this.messages.add("YOLO Swag!");
    }

    @Override
    public void handle(Willie bot, Channel channel, User sender, String[] args) {
        channel.sendMessage(String.format(this.messages.get(rand.nextInt(this.messages.size())), sender.getNick()));
    }
}
