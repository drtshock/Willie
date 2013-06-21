package com.drtshock.willie.command.utility;

import com.drtshock.willie.Willie;
import com.drtshock.willie.WillieConfig;
import com.drtshock.willie.auth.Auth;
import com.drtshock.willie.command.CommandHandler;
import org.pircbotx.Channel;
import org.pircbotx.Colors;
import org.pircbotx.User;

public class LogCommandHandler implements CommandHandler {
    @Override
    public void handle(Willie bot, Channel channel, User sender, String[] args) {
        WillieConfig config = bot.getConfig();
        // Only for channel ops or bot admins
        if(!channel.getOps().contains(sender) || !Auth.checkAuth(sender).isAdmin()) {
            return;
        }

        if(args.length == 0) {
            if(config.getLogBlacklist().contains(channel.getName())) args = new String[] {"enable"};
            else args = new String[] {"disable"};
        }

        else {
            switch(args[0].toLowerCase()) {
                case "enable":
                case "on":
                case "yes":
                case "start":
                    if(config.getLogBlacklist().contains(channel.getName())) {
                        config.getLogBlacklist().remove(channel.getName());
                        channel.sendMessage(Colors.GREEN + bot.getNick() + " is watching you.");
                        bot.save();
                    }
                    break;

                case "disable":
                case "off":
                case "no":
                case "stop":
                    if(!config.getLogBlacklist().contains(channel.getName())) {
                        config.getLogBlacklist().add(channel.getName());
                        channel.sendMessage(Colors.DARK_GRAY + "Who turned out the lights?");
                        bot.save();
                    }
                    break;
            }
        }
    }
}
