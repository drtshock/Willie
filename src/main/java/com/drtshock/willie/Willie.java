package com.drtshock.willie;

import com.drtshock.willie.config.WillieChannelConfig;
import com.drtshock.willie.config.WillieChannelConfigOption;
import com.drtshock.willie.config.WillieConfig;
import com.drtshock.willie.util.FileUtil;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

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
        // TODO: export instantiation from config directory to Willie.fromConfigDirectory() OR a WillieBuilder object
        // TODO: remove magic numbers

        File willieConfigDirectory = null;

        try {
            willieConfigDirectory = new File(FileUtil.getWillieDirectory(), "config");
        } catch (URISyntaxException e) {
            // TODO: raise a fuss
            e.printStackTrace();
        }

        if (!willieConfigDirectory.isDirectory()) {
            boolean greatSuccess = willieConfigDirectory.mkdir();

            if (!greatSuccess) {
                // TODO: raise a fuss
            }
        }

        File willieConfigFile = new File(willieConfigDirectory, "config.yml");
        WillieConfig resultingConfig = null;

        if (willieConfigFile.isFile()) {
            try {
                resultingConfig = WillieConfig.fromYamlFile(willieConfigFile);
            } catch (IOException e) {
                // TODO: raise a fuss
                e.printStackTrace();
            }
        } else {
            resultingConfig = new WillieConfig();
            try {
                Files.write(Paths.get(willieConfigFile.getPath()), resultingConfig.toYaml().getBytes());
            } catch (IOException e) {
                // TODO: raise a fuss
                e.printStackTrace();
            }
        }

        File channelConfigDirectory = new File(willieConfigDirectory, "channel");

        if (channelConfigDirectory.isDirectory()) {
            File[] potentialChannelConfigs = channelConfigDirectory.listFiles();

            for (File potentialChannelConfig : potentialChannelConfigs) {
                String lowerCaseFileName = potentialChannelConfig.getName().toLowerCase();

                if (!lowerCaseFileName.endsWith(".yml")) {
                    continue;
                }

                try {
                    WillieChannelConfig channelConfig = WillieChannelConfig.fromYamlFile(potentialChannelConfig);
                    resultingConfig.putChannelConfig(lowerCaseFileName.substring(0, lowerCaseFileName.length() - 4), channelConfig);
                } catch (IOException e) {
                    // TODO: raise a fuss
                    e.printStackTrace();
                }
            }
        } else {
            boolean greatSuccess = channelConfigDirectory.mkdir();

            if (!greatSuccess) {
                // TODO: raise a fuss
            }
        }

        Willie willie = new Willie(resultingConfig);
    }
}