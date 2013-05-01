package com.drtshock.willie;

import java.io.IOException;

import org.pircbotx.Colors;
import org.pircbotx.PircBotX;
import org.pircbotx.exception.IrcException;
import org.pircbotx.exception.NickAlreadyInUseException;
import org.pircbotx.hooks.Listener;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;

@SuppressWarnings("rawtypes")
public class Willie extends ListenerAdapter implements Listener {

    @Override
    public void onMessage(MessageEvent event) {
        String[] args = event.getMessage().split(" ");
        args[0] = args[0].substring(1);
        if(event.getMessage().replaceAll("(?i)willie", "").length() != event.getMessage().length()) {
            event.getChannel().sendMessage("Hello :3");
        }
        if(args[0].equalsIgnoreCase("repo")) {
            event.getChannel().sendMessage(Colors.BLUE + "Contribute if you feel so led: https://github.com/drtshock/willie");
        } else if(args[0].equalsIgnoreCase(".od")) {
            if(args.length == 1) {
                event.getChannel().sendMessage(Colors.PURPLE + "ObsidianDestroyer http://dev.bukkit.org/server-mods/obsidiandestroyer" + Colors.DARK_GRAY + " Blow up obsidian and other non explosive blocks with TNT. Define the durability of blocks in the config.");
            } else if(args.length == 2) {
                if(args[1].equalsIgnoreCase("release")) {
                    try {
                        String[] ret = CheckUpdate.getUpdate("obsidiandestroyer");
                        event.getChannel().sendMessage(Colors.PURPLE + "Latest release: " + ret[0] + " | Link: " + ret[1]);
                    } catch(IOException e) {
                        event.getChannel().sendMessage(Colors.RED + "Unable to find that plugin!");
                    }
                }
            }
        } else if(args[0].equalsIgnoreCase("pv")) {
            if(args.length == 1) {
                event.getChannel().sendMessage(Colors.BLUE + "PlayerVaults http://dev.bukkit.org/server-mods/playervaults" + Colors.DARK_GRAY + " Private inventories accessible by command and now by signs! Works with all item meta and is version independent.");
            } else if(args.length == 2) {
                if(args[1].equalsIgnoreCase("release")) {
                    try {
                        String[] ret = CheckUpdate.getUpdate("playervaults");
                        event.getChannel().sendMessage(Colors.PURPLE + "Latest release: " + ret[0] + " | Link: " + ret[1]);
                    } catch(IOException e) {
                        event.getChannel().sendMessage(Colors.RED + "Unable to find that plugin!");
                    }
                }
            }
        } else if(args[0].equalsIgnoreCase("latest")) {
            if(args.length == 1) {
                event.getChannel().sendMessage(Colors.RED + "Check latest BukkitDev version of a plugin by doing .latest <slug>");
            } else {
                try {
                    String[] ret = CheckUpdate.getUpdate(args[1]);
                    event.getChannel().sendMessage(Colors.PURPLE + "Latest release: " + ret[0] + " | Link: " + ret[1]);
                } catch(IOException e) {
                    event.getChannel().sendMessage(Colors.RED + "Unable to find that plugin!");
                }
            }
        }
    }

    public static void main(String[] args) throws IOException, IrcException, NickAlreadyInUseException {
        String[] channels = { "#drtshock" };
        PircBotX bot = new PircBotX();
        bot.setName("Willie2");
        bot.setVerbose(true);
        bot.getListenerManager().addListener(new Willie());
        bot.connect("irc.esper.net");
        for(String c:channels) {
            bot.joinChannel(c);
        }
    }
}
