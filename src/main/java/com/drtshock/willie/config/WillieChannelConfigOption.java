package com.drtshock.willie.config;

import java.util.ArrayList;

public enum WillieChannelConfigOption {
    ENABLED_MODULES("enabled-modules", new String[] {});

    private final String optionNode;
    private final Object defaultValue;

    private WillieChannelConfigOption(String optionNode) {
        this(optionNode, null);
    }

    private WillieChannelConfigOption(String optionNode, Object defaultValue) {
        this.optionNode = optionNode;
        this.defaultValue = defaultValue;
    }

    public String getOptionNode() {
        return this.optionNode;
    }

    public Object getDefaultValue() {
        return this.defaultValue;
    }

    public static WillieChannelConfigOption fromOptionNode(String optionNode) {
        for (WillieChannelConfigOption willieChannelConfigOption : WillieChannelConfigOption.values()) {
            if (willieChannelConfigOption.getOptionNode().equals(optionNode)) {
                return willieChannelConfigOption;
            }
        }

        return null;
    }
}
