package com.drtshock.willie.configuration;

import org.pircbotx.Channel;

public class Configuration extends AbstractConfiguration {

    public Configuration(String fileName) {
        super(fileName);
    }

    public Configuration(Channel channel) {
        super(channel);
    }


}
