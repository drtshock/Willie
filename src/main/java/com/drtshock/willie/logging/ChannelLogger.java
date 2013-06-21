package com.drtshock.willie.logging;

import org.pircbotx.Channel;
import org.pircbotx.User;
import org.pircbotx.UserSnapshot;

public interface ChannelLogger {
    public void writeMessage(User user, String message);
    public void writeJoin(User user);
    public void writePart(User user);
    public void writeQuit(UserSnapshot user, String reason);
    public void writeNickChange(User user, String oldNick, String newNick);
    public void rotate();
    public void close();
    public void flush();
    public Channel getChannel();
}
