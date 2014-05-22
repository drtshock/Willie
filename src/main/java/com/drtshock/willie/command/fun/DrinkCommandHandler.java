package com.drtshock.willie.command.fun;

import com.drtshock.willie.Willie;
import com.drtshock.willie.command.CommandHandler;
import org.pircbotx.Channel;
import org.pircbotx.Colors;
import org.pircbotx.User;

import java.util.ArrayList;
import java.util.List;

/**
 * @author drtshock
 */
public class DrinkCommandHandler implements CommandHandler {
    private List<String> messages;

    public DrinkCommandHandler() {
        this.messages = new ArrayList<>();

        this.messages.add(Colors.NORMAL + "mixes %s a drink!");
        this.messages.add(Colors.NORMAL + "gives %s a wine cooler.");
        this.messages.add(Colors.NORMAL + "pours %s a shot of rum!");
        this.messages.add(Colors.NORMAL + "pours %s a jaeger bomb!");
    }

    @Override
    public void handle(Willie bot, Channel channel, User sender, String[] args) {
        String message;
        message = String.format(this.messages.get(rand.nextInt(this.messages.size())), (args.length > 0) ? args[0] : sender.getNick());
        bot.sendAction(channel, message);
    }
}
