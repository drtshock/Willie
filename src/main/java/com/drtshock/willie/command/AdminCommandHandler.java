package com.drtshock.willie.command;

import com.drtshock.willie.Willie;
import org.pircbotx.Channel;
import org.pircbotx.Colors;
import org.pircbotx.User;

public class AdminCommandHandler implements CommandHandler {
    @Override
    public void handle(Willie bot, Channel channel, User sender, String[] args) {
        if(args.length == 0) {
            args = new String[]{"list"};
        }
        switch(args[0].toLowerCase()) {
            case "add":
                if(args.length != 2) return;
                User user = bot.getUser(args[1]);
                if(sender.getNick().toLowerCase().equals(args[1].toLowerCase())) {
                    channel.sendMessage(Colors.BLUE + "Huh?");
                    return;
                }
                else if(bot.getConfig().getAdmins().contains(args[1])) {
                    channel.sendMessage(Colors.GREEN + String.format("%s is already an admin.", args[1]));
                }
                else {
                    Willie.AuthResponse auth = bot.getAuth(user);
                    if(auth.isLoggedIn) {
                        bot.getConfig().addAdmin(auth.accountName);
                        channel.sendMessage(Colors.GREEN + String.format("Added %s as an admin!", args[1]));
                        bot.sendMessage(args[1], Colors.GREEN + "You've been added as an admin!");
                    }
                    else {
                        channel.sendMessage(Colors.RED + String.format("%s is not identified with NickServ", user.getNick()));
                    }
                }
                break;
            case "remove":
            case "del":
            case "delete":
                if(args.length != 2) return;
                else if(sender.getNick().toLowerCase().equals(args[1].toLowerCase())) {
                    channel.sendMessage(Colors.CYAN + "Huh?");
                    return;
                }
                else if(!bot.getConfig().getAdmins().contains(args[1])) {
                    channel.sendMessage(Colors.RED + String.format("%s is not an admin.", args[1]));
                }
                else {
                    bot.getConfig().removeAdmin(args[1]);
                    channel.sendMessage(Colors.RED + String.format("Removed %s from the admin list.", args[1]));
                    bot.sendMessage(args[1], Colors.RED + "Sorry, you're no longer an admin.");
                }
                break;
            case "list":
                channel.sendMessage("The admins are: " + bot.getConfig().getAdmins());
                break;
        }
    }
}
