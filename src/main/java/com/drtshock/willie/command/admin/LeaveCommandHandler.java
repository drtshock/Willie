package com.drtshock.willie.command.admin;

import com.drtshock.willie.Willie;
import com.drtshock.willie.command.CommandHandler;
import org.pircbotx.Channel;
import org.pircbotx.User;

import java.util.ArrayList;

public class LeaveCommandHandler implements CommandHandler {

    @Override
    public void handle(Willie bot, Channel channel, User sender, String[] args) {
        if (bot.getUserBot().getChannels().size() == 1) {
            channel.sendMessage("Please don't make me leave!");
        } else if (args.length >= 1) {
            ArrayList<String> channels = new ArrayList<>();

            for (String s : args[0].split(",")) {
                if (!s.startsWith("#")) {
                    s = "#" + s;
                    if (!channels.contains(s)) {
                        channels.add(s);
                    }
                }
            }

            ArrayList<Channel> leaveChannels = new ArrayList<>();

            for (String s : channels) {
                leaveChannels.add(bot.getChannel(s));
            }

            if (leaveChannels.size() < bot.getUserBot().getChannels().size()) {
                ArrayList<Channel> inChannel = new ArrayList<>();
                ArrayList<Channel> notInChannel = new ArrayList<>();

                for (Channel c : leaveChannels) {
                    if (bot.isOnChannel(c.getName())) {
                        if (!(args.length >= 2 && args[1].equalsIgnoreCase("silent"))) {
                            c.sendMessage(sender.getNick() + "%s says I don't belong here...");
                        }

                        bot.partChannel(c);
                        inChannel.add(c);
                    } else {
                        notInChannel.add(c);
                    }
                }

                StringBuilder sb = new StringBuilder();
                if (inChannel.size() >= 1) {
                    sb.append("Leaving channel");

                    if (inChannel.size() > 1) {
                        sb.append("s");
                    }

                    sb.append(" ");

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

                    if (notInChannel.size() >= 1) {
                        sb.append(" but ");
                    }
                }

                if (notInChannel.size() >= 1) {
                    sb.append("was I supposed to be in ");
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
                    sb.append("?");
                }

                channel.sendMessage(sb.toString());
            } else {
                channel.sendMessage("Please don't make me leave!");
            }
        } else {
            bot.partChannel(channel);
        }
    }
}
