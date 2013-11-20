package com.drtshock.willie.configuration;

import org.pircbotx.Channel;

public class WillieConfiguration extends AbstractConfiguration {

    public WillieConfiguration(String fileName) {
        super(fileName);
    }

    public WillieConfiguration(Channel channel) {
        super(channel);
    }


}
