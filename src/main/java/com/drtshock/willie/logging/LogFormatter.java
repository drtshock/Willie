package com.drtshock.willie.logging;

import org.pircbotx.Channel;
import org.pircbotx.User;
import org.pircbotx.UserSnapshot;

public interface LogFormatter {
    public String getFileExtension();
    public String getHeader();
    public String formatMessage(String network, Channel channel, User user, String message);
    public String formatJoin(String network, Channel channel, User user);
    public String formatPart(String network, Channel channel, User user);
    public String formatQuit(String network, Channel channel, UserSnapshot user, String reason);
    public String formatNickChange(String network, Channel channel, User user, String oldNick, String newNick);
    public String getFooter();
}
