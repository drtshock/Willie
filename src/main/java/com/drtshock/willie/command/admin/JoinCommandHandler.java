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

            List<String> alreadyIn = new ArrayList<>();
            List<String> joined = new ArrayList<>();
            List<String> nonexistent = new ArrayList<>();

            for (String c : channels) {
                if (bot.isOnChannel(c)) {
                    alreadyIn.add(c);
                } else {
                    if (bot.channelExists(c)) {
                        joined.add(c);
                        if (!(args.length == 2 && args[1].equalsIgnoreCase("silent"))) {
                            bot.getChannel(args[0]).sendMessage(sender.getNick() + " told me I belong here.");
                        }
                        bot.joinChannel(c);
                    } else {
                        nonexistent.add(c);
                    }
                }
            }

            StringBuilder sb = new StringBuilder();
            if (!joined.isEmpty()) {
                sb.append("See you in ");

                for (int i = 0; i < joined.size(); i++) {
                    sb.append(joined.get(i));

                    if (i < joined.size() - 1) {
                        if (joined.size() > 2) {
                            sb.append(",");
                        }
                        sb.append(" ");
                    }

                    if (i == joined.size() - 2) {
                        sb.append("and ");
                    }
                }

                sb.append("!");

                if (alreadyIn.size() >= 1) {
                    sb.append(" ");
                }
            }

            if (!alreadyIn.isEmpty()) {
                sb.append("I was already in ");

                for (int i = 0; i < alreadyIn.size(); i++) {
                    sb.append(alreadyIn.get(i));

                    if (i < alreadyIn.size() - 1) {
                        if (alreadyIn.size() > 2) {
                            sb.append(",");
                        }
                        sb.append(" ");
                    }

                    if (i == alreadyIn.size() - 2) {
                        sb.append("and ");
                    }
                }

                sb.append("though...");

                if (!nonexistent.isEmpty()) {
                    sb.append(" And ");
                }
            }

            if (!nonexistent.isEmpty()) {
                for (int i = 0; i < nonexistent.size(); i++) {
                    sb.append(nonexistent.get(i));

                    if (i < nonexistent.size() - 1) {
                        if (nonexistent.size() > 2) {
                            sb.append(",");
                        }
                        sb.append(" ");
                    }

                    if (i == nonexistent.size() - 2) {
                        sb.append("and ");
                    }
                }

                if (nonexistent.size() > 2) {
                    sb.append(" don't even exist...");
                } else {
                    sb.append(" doesn't even exist...");
                }
            }

            channel.sendMessage(sb.toString());
        }
    }
}
