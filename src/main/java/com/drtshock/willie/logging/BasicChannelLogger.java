package com.drtshock.willie.logging;

import org.pircbotx.Channel;
import org.pircbotx.User;
import org.pircbotx.UserSnapshot;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

//TODO Log rotations can probably be handled more elegantly using a scheduled executor

public class BasicChannelLogger implements ChannelLogger {
    private static final Logger logger = Logger.getLogger(BasicChannelLogger.class.getName());

    private final Channel channel;
    private final String logDirectory;
    private final String networkName;
    private final LogFormatter logFormatter;

    private Date currentDate = new Date();
    private final SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
    private PrintWriter writer;


    public BasicChannelLogger(String networkName, Channel channel, String logDirectory, LogFormatter logFormatter) {
        this.channel = channel;
        this.logDirectory = logDirectory;
        this.networkName = networkName;
        this.logFormatter = logFormatter;
        rotate();
    }

    public void writeMessage(User user, String message) {
        if(writer == null) return;
        if(!dateFormatter.format(new Date()).equals(dateFormatter.format(currentDate))) rotate();
        String logData = logFormatter.formatMessage(networkName, channel, user, message);
        writer.println(logData);
    }

    public void writeJoin(User user) {
        if(writer == null) return;
        if(!dateFormatter.format(new Date()).equals(dateFormatter.format(currentDate))) rotate();
        String logData = logFormatter.formatJoin(networkName, channel, user);
        writer.println(logData);
    }

    public void writePart(User user) {
        if(writer == null) return;
        if(!dateFormatter.format(new Date()).equals(dateFormatter.format(currentDate))) rotate();
        String logData = logFormatter.formatPart(networkName, channel, user);
        writer.println(logData);
    }

    @Override
    public void writeQuit(UserSnapshot user, String reason) {
        if(writer == null) return;
        if(!dateFormatter.format(new Date()).equals(dateFormatter.format(currentDate))) rotate();
        String logData = logFormatter.formatQuit(networkName, channel, user, reason);
        writer.println(logData);
    }

    @Override
    public void writeNickChange(User user, String oldNick, String newNick) {
        if(writer == null) return;
        if(!dateFormatter.format(new Date()).equals(dateFormatter.format(currentDate))) rotate();
        String logData = logFormatter.formatNickChange(networkName, channel, user, oldNick, newNick);
        writer.println(logData);
    }

    public void rotate() {
        if(writer != null) {
            writer.write(logFormatter.getFooter());
            writer.close();
        }

        // Create new log file
        String date = dateFormatter.format(currentDate);
        String logFilename = logDirectory + "/"
                + networkName + "/"
                + channel.getName() + "/"
                + date + logFormatter.getFileExtension();
        File file = new File(logFilename);
        try {
            // Create new file and write header if necessary
            if(!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
                writer = new PrintWriter(new FileOutputStream(file, true), true);
                writer.println(logFormatter.getHeader());
            }
            else {
                writer = new PrintWriter(new FileOutputStream(file, true), true);
            }
        } catch(IOException ex) {
            logger.severe("Unable to create channel log file at " + file.getAbsolutePath());
            return;
        }
    }

    public void close() {
        if(writer != null) writer.close();
    }

    public void flush() {
        if(writer != null) writer.flush();
    }

    public Channel getChannel() {
        return channel;
    }
}
