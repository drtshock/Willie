package com.drtshock.willie.command.admin;

import com.drtshock.willie.Willie;
import com.drtshock.willie.command.CommandHandler;
import org.pircbotx.Channel;
import org.pircbotx.User;

import java.util.List;

/**
 * @author KingFaris10
 */
public class IgnoreChannelCommandHandler implements CommandHandler {

    @Override
    public void handle(Willie bot, Channel channel, User sender, String[] args) {
        if (args.length == 1) {
            String targetChannel = args[0].toLowerCase();
            if (!targetChannel.startsWith("#")) targetChannel = "#" + targetChannel;
            if (Willie.getInstance().channelExists(targetChannel)) {
                List<String> ignoredChannels = Willie.getInstance().getConfig().getIgnoredChannels(targetChannel);
                String strIgnoredChannels = "";
                for (int index = 0; index < ignoredChannels.size(); index++) {
                    if (index == ignoredChannels.size() - 1) strIgnoredChannels += ignoredChannels.get(index);
                    else strIgnoredChannels += ignoredChannels.get(index) + ", ";
                }
                if (ignoredChannels.isEmpty()) {
                    channel.sendMessage(targetChannel + "'s ignored commands: None!");
                } else {
                    channel.sendMessage(targetChannel + "'s ignored commands: " + strIgnoredChannels.toLowerCase());
                }
            } else {
                sender.sendMessage(targetChannel + " does not exist!");
            }
        } else if (args.length == 2) {
            String targetChannel = args[0].toLowerCase();
            if (!targetChannel.startsWith("#")) targetChannel = "#" + targetChannel;
            if (Willie.getInstance().channelExists(targetChannel)) {
                if (sender.getChannelsOwnerIn().contains(channel) || sender.getChannelsOpIn().contains(channel)) {
                    String targetCommand = args[1].toLowerCase();
                    if (bot.getConfig().isIgnoredChannel(targetChannel, targetCommand)) {
                        bot.getConfig().removeIgnoredChannel(targetChannel, targetCommand);
                    } else {
                        bot.getConfig().addIgnoredChannel(targetChannel, targetCommand);
                    }
                    bot.save();
                    bot.sendMessage(targetChannel, sender.getNick() + " added '" + targetCommand + "' to the ignored commands list.");
                    if (!sender.getChannels().contains(channel))
                        sender.sendMessage("You added '" + targetCommand + "' to the ignored commands list.");
                } else {
                    sender.sendMessage("You cannot add ignored commands to other channels you're not OP in!");
                }
            } else {
                sender.sendMessage(targetChannel + " does not exist!");
            }
        } else {
            List<String> ignoredChannels = Willie.getInstance().getConfig().getIgnoredChannels(channel.getName());
            String strIgnoredChannels = "";
            for (int index = 0; index < ignoredChannels.size(); index++) {
                if (index == ignoredChannels.size() - 1) strIgnoredChannels += ignoredChannels.get(index);
                else strIgnoredChannels += ignoredChannels.get(index) + ", ";
            }
            if (ignoredChannels.isEmpty()) {
                channel.sendMessage(channel.getName().toLowerCase() + "'s ignored commands: None!");
            } else {
                channel.sendMessage(channel.getName().toLowerCase() + "'s ignored commands: " + strIgnoredChannels.toLowerCase());
            }
        }
    }
}
