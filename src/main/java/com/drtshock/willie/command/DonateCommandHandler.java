package com.drtshock.willie.command;

import com.drtshock.willie.Willie;
import org.pircbotx.Channel;
import org.pircbotx.Colors;
import org.pircbotx.User;

import java.util.ArrayList;
import java.util.Random;

public class DonateCommandHandler implements CommandHandler {

    private Random rand;
    private ArrayList<String> messages;

    public DonateCommandHandler() {
        this.rand = new Random();
        this.messages = new ArrayList<String>();

        this.messages.add(Colors.DARK_GREEN + "Buy me food :3 %s");
        this.messages.add(Colors.DARK_GREEN + "Donations are nice. %s");
        this.messages.add(Colors.DARK_GREEN + "Hey there %s");
        this.messages.add(Colors.DARK_GREEN + "Buy dirt for drt %s");
    }

    @Override
    public void handle(Willie bot, Channel channel, User sender, String[] args) {
        String message = String.format(this.messages.get(this.rand.nextInt(this.messages.size())), bot.getConfig().getDonateUrl());
        channel.sendMessage(message);
    }

}
