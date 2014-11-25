package com.drtshock.willie.config;

import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

/**
 * A class representing configuration settings for WillieConfigOptions globally across IRC.
 */
public class WillieConfig {
    /**
     * Contains the values set for each WillieChannelConfigOption.
     */
    private final EnumMap<WillieConfigOption, Object> configMap = new EnumMap<>(WillieConfigOption.class);

    /**
     * Contains children WillieChannelConfigs, stored by channel name
     */
    private final HashMap<String, WillieChannelConfig> channelConfigsMap = new HashMap<>();

    /**
     * Gets the value specified, else the default value, for a specified option.
     *
     * @param option option to get the value of
     * @return the value specified, else the default value
     */
    public Object getOptionValue(WillieConfigOption option) {
        // if the value has been specified, that value is preferred
        if (configMap.containsKey(option)) {
            return configMap.get(option);
        }

        // if not, resort to the default value
        return option.getDefaultValue();
    }

    /**
     * Puts the chosen value for an option into storage, so it will be preferred to the default value for the option.
     *
     * @param option option to set the preferred value of
     * @param value value preferred to be used, instead of the default value
     */
    public void putOptionValue(WillieConfigOption option, Object value) {
        // store the value so it will be retrieved if queried
        configMap.put(option, value);
    }

    /**
     * Gets the chosen WillieChannelConfig for a specified channel, else the default WillieChannelConfig options
     *
     * @param channelName name of the channel to find the settings of
     * @return the WillieChannelConfig for the specified channel, else the default WillieChannelConfig options
     */
    public WillieChannelConfig getChannelConfig(String channelName) {
        // if the WillieChannelConfig has been specified, that is preferred
        if (channelConfigsMap.containsKey(channelName)) {
            return channelConfigsMap.get(channelName);
        }

        // if not, resort to the default WillieChannelConfig
        return new WillieChannelConfig();
    }

    /**
     * Puts the WillieChannelConfig into storage, so it will be preferred to the default WillieChannelConfig options
     *
     * @param channelName name of the channel to set the preferred WillieChannelConfig of
     * @param channelConfig WillieChannelConfig preffered to be used, instead of the default WillieChannelConfig
     */
    public void putChannelConfig(String channelName, WillieChannelConfig channelConfig) {
        // store the value so it will be retrieved if queried
        channelConfigsMap.put(channelName, channelConfig);
    }

    /**
     * Serialise the map into Yaml format, with options and preferred values else default stored.
     *
     * @return the map in Yaml form
     */
    public String toYaml() {
        Map<String, Object> valueMap = new HashMap<>();
        for (WillieConfigOption willieConfigOption : WillieConfigOption.values()) {
            valueMap.put(willieConfigOption.getOptionNode(), this.getOptionValue(willieConfigOption));
        }
        Yaml yaml = new Yaml();
        return yaml.dump(valueMap);
    }

    /**
     * Instantiate a WillieConfig Object from a serialised map form in Yaml.
     *
     * @param yamlData Yaml in the form of a string
     * @return the WillieConfig object with the values defined in the Yaml
     */
    public static WillieConfig fromYaml(String yamlData) {
        Yaml yaml = new Yaml();
        Map<String, Object> valueMap = (Map<String, Object>) yaml.load(yamlData);
        WillieConfig willieConfig = new WillieConfig();
        for (Map.Entry<String, Object> entry : valueMap.entrySet()) {
            WillieConfigOption matchedOption = WillieConfigOption.fromOptionNode(entry.getKey());
            // TODO: check entry value matches the type required by matchedOption
            if (matchedOption == null) {
                // TODO: raise a fuss
                continue;
            }
            willieConfig.putOptionValue(matchedOption, entry.getValue());
        }
        return willieConfig;
    }

    /**
     * Instantiate a WillieConfig Object from a serialised map form in Yaml, stored in a file.
     *
     * @param yamlFile file in which the yaml is encoded in
     * @return the WillieConfig object with the values defined in the Yaml
     * @throws IOException if there was an error reading the file
     */
    public static WillieConfig fromYamlFile(File yamlFile) throws IOException {
        String yamlData = new String(Files.readAllBytes(Paths.get(yamlFile.toURI())));
        return WillieConfig.fromYaml(yamlData);
    }

    /**
     * Instantiate a WillieConfig Object from a serialised map form in Yaml and children WillieChannelConfig Objects
     * from their serialised map form in Yaml.
     *
     * Folders should be laid out like so:
     * |--config (is willieConfigDirectory)
     * |--|--config.yml
     * |--|--channel
     * |--|--|--#<channel name in lower case>.yml
     *
     * @param willieConfigDirectory the directory in which to look for the configuration files
     * @return the WillieConfig object with the values defined in the Yaml
     */
    public static WillieConfig fromYamlDirectory(File willieConfigDirectory) {
        // TODO: remove magic numbers
        // if the config directory doesn't exist
        if (!willieConfigDirectory.isDirectory()) {
            // create it
            boolean greatSuccess = willieConfigDirectory.mkdir();
            if (!greatSuccess) {
                // TODO: raise a fuss
            }
        }

        // the Yaml file for the WillieConfig object
        File willieConfigFile = new File(willieConfigDirectory, "config.yml");
        WillieConfig resultingConfig = null;

        // if the WillieConfig object Yaml file exists
        if (willieConfigFile.isFile()) {
            try {
                // load yaml data
                resultingConfig = WillieConfig.fromYamlFile(willieConfigFile);
            } catch (IOException e) {
                resultingConfig = new WillieConfig();

                // TODO: raise a fuss
                e.printStackTrace();
            }
        } else {
            // use default config
            resultingConfig = new WillieConfig();
            try {
                boolean greatSuccess = willieConfigFile.createNewFile();

                if (!greatSuccess) {
                    // TODO: raise a fuss
                }

                // write default config
                Files.write(Paths.get(willieConfigFile.getPath()), resultingConfig.toYaml().getBytes());
            } catch (IOException e) {
                // TODO: raise a fuss
                e.printStackTrace();
            }
        }

        // the directory in which WillieChannelConfig files serialised are stored
        File channelConfigDirectory = new File(willieConfigDirectory, "channel");

        // if the directory does not exist, create it
        if (!channelConfigDirectory.isDirectory()) {
            boolean greatSuccess = channelConfigDirectory.mkdir();

            if (!greatSuccess) {
                // TODO: raise a fuss
            }
        }

        // for every default channel
        for (String channelName : (ArrayList<String>) resultingConfig.getOptionValue(WillieConfigOption.DEFAULT_CHANNELS)) {
            // the WillieChannelConfig file serialised for the channel with the channelName
            File channelConfigFile = new File(channelConfigDirectory, channelName.toLowerCase() + ".yml");
            WillieChannelConfig resultingChannelConfig = null;

            // if the file exists
            if (channelConfigFile.isFile()) {
                try {
                    // load it from Yaml
                    resultingChannelConfig = WillieChannelConfig.fromYamlFile(channelConfigFile);
                } catch (IOException e) {
                    resultingChannelConfig = new WillieChannelConfig();
                    // TODO: raise a fuss
                    e.printStackTrace();
                }
            } else {
                // use the default WillieChannelconfig values
                resultingChannelConfig = new WillieChannelConfig();

                try {
                    // write the default config
                    Files.write(Paths.get(channelConfigFile.getPath()), resultingChannelConfig.toYaml().getBytes());
                } catch (IOException e) {
                    // TODO: raise a fuss
                    e.printStackTrace();
                }
            }
            // store the WillieChannelConfig in the newly created WillieConfig
            resultingConfig.putChannelConfig(channelName, resultingChannelConfig);
        }
        return resultingConfig;
    }
}
