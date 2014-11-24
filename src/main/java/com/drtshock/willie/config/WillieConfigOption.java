package com.drtshock.willie.config;

import java.util.ArrayList;

public enum WillieConfigOption {

    BIND_HOST("bind-host"),
    BIND_PORT("bind-port", 6667),
    SERVER_HOST("server-host", "irc.esper.net"),
    SERVER_PORT("server-port", 5555),
    SERVER_PASSWORD("server-password"),
    NAME("name", "Willie"),
    NICK("nick", "Willie"),
    REAL_NAME("real-name", "Willie"),
    USER("user", "Willie"),
    DEFAULT_CHANNELS("default-channels", new ArrayList<String>());

    private final String optionNode;
    private final Object defaultValue;

    private WillieConfigOption(String optionNode) {
        this(optionNode, null);
    }

    private WillieConfigOption(String optionNode, Object defaultValue) {
        this.optionNode = optionNode;
        this.defaultValue = defaultValue;
    }

    public String getOptionNode() {
        return this.optionNode;
    }

    public Object getDefaultValue() {
        return this.defaultValue;
    }

    public static WillieConfigOption fromOptionNode(String optionNode) {
        for (WillieConfigOption willieConfigOption : WillieConfigOption.values()) {
            if (willieConfigOption.getOptionNode().equals(optionNode)) {
                return willieConfigOption;
            }
        }
        return null;
    }
}
