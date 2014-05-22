package com.drtshock.willie.command.fun;

import com.drtshock.willie.Willie;
import com.drtshock.willie.command.CommandHandler;
import org.pircbotx.Channel;
import org.pircbotx.User;

import java.util.ArrayList;
import java.util.List;

public class PopcornCommandHandler implements CommandHandler {
    private List<String> messages;

    public PopcornCommandHandler() {
        this.messages = new ArrayList<>();

        this.messages.add("pops some plain popcorn.");
        this.messages.add("pops some popcorn with butter.");
        this.messages.add("pops some popcorn with salt and butter!");
        this.messages.add("pops some kettle corn.");
    }

    @Override
    public void handle(Willie bot, Channel channel, User sender, String[] args) {
        bot.sendAction(channel, this.messages.get(rand.nextInt(this.messages.size())));
    }
}
