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
public class CakeCommandHandler implements CommandHandler {
    private List<String> messages;

    public CakeCommandHandler() {
        this.messages = new ArrayList<String>();

        this.messages.add("The cake is a lie!");
        this.messages.add("The cake isn't a lie!");
        this.messages.add("The cake could be a lie!");
        this.messages.add("Think with portals");
    }

    @Override
    public void handle(Willie bot, Channel channel, User sender, String[] args) {
        channel.sendMessage(this.messages.get(rand.nextInt(this.messages.size())));
    }
}
