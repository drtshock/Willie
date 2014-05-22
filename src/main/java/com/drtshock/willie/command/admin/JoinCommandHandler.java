package com.drtshock.willie.command.admin;

import com.drtshock.willie.Willie;
import com.drtshock.willie.command.CommandHandler;
import org.pircbotx.Channel;
import org.pircbotx.User;

import java.util.ArrayList;
import java.util.List;

public class JoinCommandHandler implements CommandHandler {

    @Override
    public void handle(Willie bot, Channel channel, User sender, String[] args) {
        if (args.length <= 2) {
            List<String> channels = new ArrayList<>();

            for (String s : args[0].split(",")) {
                if (!s.startsWith("#")) {
                    s = "#" + s;
                    if (!channels.contains(s)) {
                        channels.add(s);
                    }
                }
            }

            List<Channel> joinChannels = new ArrayList<>();

            for (String s : channels) {
                joinChannels.add(bot.getChannel(s));
            }

            List<Channel> inChannel = new ArrayList<>();
            List<Channel> notInChannel = new ArrayList<>();

            for (Channel c : joinChannels) {
                if (bot.isOnChannel(c.getName())) {
                    inChannel.add(c);
                } else {
                    notInChannel.add(c);
                    if (!(args.length == 2 && args[1].equalsIgnoreCase("silent"))) {
                        bot.getChannel(args[0]).sendMessage(sender.getNick() + " told me I belong here.");
                    }
                    bot.joinChannel(c.getName());
                }
            }

            StringBuilder sb = new StringBuilder();
            if (notInChannel.size() >= 1) {
                sb.append("See you in ");

                for (int i = 0; i < notInChannel.size(); i++) {
                    sb.append(notInChannel.get(i).getName());

                    if (i < notInChannel.size() - 1) {
                        if (notInChannel.size() > 2) {
                            sb.append(",");
                        }
                        sb.append(" ");
                    }

                    if (i == notInChannel.size() - 2) {
                        sb.append("and ");
                    }
                }

                sb.append("!");

                if (inChannel.size() >= 1) {
                    sb.append(" ");
                }
            }

            if (inChannel.size() >= 1) {
                sb.append("I was already in ");

                for (int i = 0; i < inChannel.size(); i++) {
                    sb.append(inChannel.get(i).getName());

                    if (i < inChannel.size() - 1) {
                        if (inChannel.size() > 2) {
                            sb.append(",");
                        }
                        sb.append(" ");
                    }

                    if (i == inChannel.size() - 2) {
                        sb.append("and ");
                    }
                }

                sb.append("though...");
            }

            channel.sendMessage(sb.toString());
        }
    }
}
