package com.drtshock.willie.config;

import java.util.ArrayList;

/**
 * An enum representing configurable options globally.
 */
public enum WillieConfigOption {

    /**
     * The host to bind to, if required; useful for servers with multiple IPs. Optional.
     */
    BIND_HOST("bind-host"),

    /**
     * The host port to bind to, if required; useful for servers with multiple IPs. Optional.
     */
    BIND_PORT("bind-port", 6667),

    /**
     * The host name of the IRC server to connect to.
     */
    SERVER_HOST("server-host", "irc.esper.net"),

    /**
     * The port of the IRC server to connect to.
     */
    SERVER_PORT("server-port", 5555),

    /**
     * The password of the IRC server to connect to. Optional.
     */
    SERVER_PASSWORD("server-password"),

    /**
     * The name to user on the IRC server.
     */
    NAME("name", "Willie"),

    /**
     * The nick to use on the IRC server.
     */
    NICK("nick", "Willie"),

    /**
     * The real name to use on the IRC server.
     */
    REAL_NAME("real-name", "Willie"),

    /**
     * The user to use on the IRC server.
     */
    USER("user", "Willie"),

    /**
     * The default channels to connect to on the IRC server.
     */
    DEFAULT_CHANNELS("default-channels", new ArrayList<String>());

    /**
     * The key that will be used in Yaml serialisation of configs.
     */
    private final String optionNode;

    /**
     * The default value of the Object; will be null if there is no default value.
     */
    private final Object defaultValue;

    /**
     * Instantiates an option with just a String key, with no default value.
     *
     * @param optionNode string key to be used in serialisation
     */
    private WillieConfigOption(String optionNode) {
        this(optionNode, null);
    }

    /**
     * Instantiates an option with a String key and a default value.
     *
     * @param optionNode string key to be used in serialisation
     * @param defaultValue value to be used initially and/or in place of a user-defined value
     */
    private WillieConfigOption(String optionNode, Object defaultValue) {
        this.optionNode = optionNode;
        this.defaultValue = defaultValue;
    }

    /**
     * Gets the key that will be used in Yaml serialisation of configs.
     *
     * @return the key
     */
    public String getOptionNode() {
        return this.optionNode;
    }

    /**
     * Gets the default value of the Object; will be null if there is no default value.
     *
     * @return the default value, or null if there is no default value
     */
    public Object getDefaultValue() {
        return this.defaultValue;
    }

    /**
     * Return the WillieChannelConfigOption that is represented by the specified option node
     *
     * @param optionNode the key that will be used in Yaml serialisation of configs
     * @return the WillieChannelConfigOption that represents the specified option node
     */
    public static WillieConfigOption fromOptionNode(String optionNode) {
        for (WillieConfigOption willieConfigOption : WillieConfigOption.values()) {
            if (willieConfigOption.getOptionNode().equals(optionNode)) {
                return willieConfigOption;
            }
        }

        return null;
    }
}
