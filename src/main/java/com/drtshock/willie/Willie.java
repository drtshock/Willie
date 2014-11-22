package com.drtshock.willie;

import com.drtshock.willie.config.WillieConfig;
import com.drtshock.willie.util.FileUtil;

import java.net.URISyntaxException;

public class Willie {
    private final WillieConfig config;

    public Willie() {
        this(new WillieConfig());
    }

    public Willie(WillieConfig config) {
        this.config = config;
    }

    public WillieConfig getConfig() {
        return this.config;
    }

    public static void main(String[] args) {
        WillieConfig config = null;
        try {
            config = WillieConfig.fromYamlDirectory(FileUtil.getWillieDirectory());
        } catch (URISyntaxException e) {
            // TODO: raise a fuss
            e.printStackTrace();
        }
        Willie willie = new Willie(config);
    }
}