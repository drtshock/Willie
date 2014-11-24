package com.drtshock.willie.config;

import java.util.ArrayList;

/**
 * An enum representing configurable options for channels.
 */
public enum WillieChannelConfigOption {
    /**
     * The modules in Willie that will act on what happens in this channel.
     */
    ENABLED_MODULES("enabled-modules", new ArrayList<String>());

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
    private WillieChannelConfigOption(String optionNode) {
        this(optionNode, null);
    }

    /**
     * Instantiates an option with a String key and a default value.
     *
     * @param optionNode string key to be used in serialisation
     * @param defaultValue value to be used initially and/or in place of a user-defined value
     */
    private WillieChannelConfigOption(String optionNode, Object defaultValue) {
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
    public static WillieChannelConfigOption fromOptionNode(String optionNode) {
        for (WillieChannelConfigOption willieChannelConfigOption : WillieChannelConfigOption.values()) {
            if (willieChannelConfigOption.getOptionNode().equals(optionNode)) {
                return willieChannelConfigOption;
            }
        }

        return null;
    }
}
