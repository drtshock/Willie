package com.drtshock.willie.logging;

import org.pircbotx.Channel;
import org.pircbotx.User;
import org.pircbotx.UserSnapshot;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BasicLogFormatter implements LogFormatter {
    private SimpleDateFormat timestampFormat = new SimpleDateFormat("HH:mm");

    @Override
    public String getFileExtension() {
        return ".txt";
    }

    @Override
    public String getHeader() {
        return "";
    }

    public String getTimestamp() {
        return timestampFormat.format(new Date());
    }

    @Override
    public String formatMessage(String network, Channel channel, User user, String message) {
        return String.format("[%1$s] <%2$s> %3$s",
                getTimestamp(),
                user.getNick(),
                message);
    }

    @Override
    public String formatJoin(String network, Channel channel, User user) {
        return String.format("[%1$s] *** %2$s [%3$s] has joined %4$s",
                getTimestamp(),
                user.getNick(),
                String.format("%s@%s", user.getLogin(), user.getHostmask()),
                channel.getName());
    }

    @Override
    public String formatPart(String network, Channel channel, User user) {
        return String.format("[%1$s] *** %2$s [%3$s] has left %4$s",
                getTimestamp(),
                user.getNick(),
                String.format("%s@%s", user.getLogin(), user.getHostmask()),
                channel.getName());
    }

    @Override
    public String formatQuit(String network, Channel channel, UserSnapshot user, String reason) {
        return String.format("[%1$s] *** %2$s [%3$s] has quit [%4$s]",
                getTimestamp(),
                user.getNick(),
                String.format("%s@%s", user.getLogin(), user.getHostmask()),
                reason);
    }

    @Override
    public String formatNickChange(String network, Channel channel, User user, String oldNick, String newNick) {
        return String.format("[%1$s] *** %2$s [%3$s] is now known as %4$s",
                getTimestamp(),
                oldNick,
                String.format("%s@%s", user.getLogin(), user.getHostmask()),
                newNick);
    }

    @Override
    public String getFooter() {
        return "";
    }
}
