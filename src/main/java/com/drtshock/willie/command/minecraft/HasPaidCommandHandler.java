package com.drtshock.willie.command.minecraft;

import com.drtshock.willie.Willie;
import com.drtshock.willie.command.CommandHandler;
import com.drtshock.willie.util.WebHelper;
import org.pircbotx.Channel;
import org.pircbotx.Colors;
import org.pircbotx.User;

import java.net.URL;
import java.net.URLEncoder;

/**
 * @author stuntguy3000
 */
public class HasPaidCommandHandler implements CommandHandler {

    @Override
    public void handle(Willie bot, Channel channel, User sender, String[] args) throws Exception {
        if (args.length != 1) {
            channel.sendMessage("Check if a Minecraft user has paid with " + bot.getConfig().getCommandPrefix() + "haspaid <username>");
            return;
        }

        String user = args[0];

        if (user.length() > 16) {
            user = user.substring(0, 16);
        }

        if (!user.matches("^[a-zA-Z0-9_]*$")) {
            channel.sendMessage(Colors.RED + "Only usernames with letters, numbers and underscores are valid!");
            return;
        }

        String result = WebHelper.readURLToString(new URL("https://minecraft.net/haspaid.jsp?user=" + URLEncoder.encode(user, "UTF-8")));

        if (result.equalsIgnoreCase("true")) {
            channel.sendMessage(Colors.GREEN + Colors.BOLD + user + Colors.NORMAL + Colors.GREEN + " has paid!");
        } else if (result.equalsIgnoreCase("false")) {
            channel.sendMessage(Colors.RED + Colors.BOLD + user + Colors.NORMAL + Colors.RED + " has NOT paid!");
        } else {
            throw new Exception("Page Output: " + result);
        }
    }
}
