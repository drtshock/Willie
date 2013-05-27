package com.drtshock.willie.command;

import com.drtshock.willie.Willie;
import org.pircbotx.Channel;
import org.pircbotx.Colors;
import org.pircbotx.User;

/**
 * @author B2OJustin
 */
public class ReloadCommandHandler implements CommandHandler {
    @Override
    public void handle(Willie bot, Channel channel, User sender, String[] args) {
        channel.sendMessage(Colors.GREEN + String.format("Yes master %s! Reloading!", sender.getNick()));
        bot.reload();
    }
}
