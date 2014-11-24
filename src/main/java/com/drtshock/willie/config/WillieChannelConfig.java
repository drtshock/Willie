package com.drtshock.willie.config;

import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

/**
 * A class representing configuration settings for WillieChannelConfigOptions for a specific channel.
 */
public class WillieChannelConfig {
    /**
     * Contains the values set for each WillieChannelConfigOption.
     */
    private final EnumMap<WillieChannelConfigOption, Object> configMap = new EnumMap<>(WillieChannelConfigOption.class);

    /**
     * Gets the value specified, else the default value, for a specified option.
     *
     * @param option option to get the value of
     * @return the value specified, else the default value
     */
    public Object getOptionValue(WillieChannelConfigOption option) {
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
    public void putOptionValue(WillieChannelConfigOption option, Object value) {
        // store the value so it will be retrieved if queried
        configMap.put(option, value);
    }

    /**
     * Serialise the map into Yaml format, with options and preferred values else default stored.
     *
     * @return the map in Yaml form
     */
    public String toYaml() {
        Map<String, Object> valueMap = new HashMap<>();
        for (WillieChannelConfigOption willieChannelConfigOption : WillieChannelConfigOption.values()) {
            valueMap.put(willieChannelConfigOption.getOptionNode(), this.getOptionValue(willieChannelConfigOption));
        }
        Yaml yaml = new Yaml();
        return yaml.dump(valueMap);
    }

    /**
     * Instantiate a WillieChannelConfig Object from a serialised map form in Yaml.
     *
     * @param yamlData Yaml in the form of a string
     * @return the WillieChannelConfig object with the values defined in the Yaml
     */
    public static WillieChannelConfig fromYaml(String yamlData) {
        Yaml yaml = new Yaml();
        Map<String, Object> valueMap = (Map<String, Object>) yaml.load(yamlData);
        WillieChannelConfig willieChannelConfig = new WillieChannelConfig();
        for (Map.Entry<String, Object> entry : valueMap.entrySet()) {
            WillieChannelConfigOption matchedOption = WillieChannelConfigOption.fromOptionNode(entry.getKey());
            // TODO: check entry value matches the type required by matchedOption
            if (matchedOption == null) {
                // TODO: raise a fuss
                continue;
            }
            willieChannelConfig.putOptionValue(matchedOption, entry.getValue());
        }
        return willieChannelConfig;
    }

    /**
     * Instantiate a WillieChannelConfig Object from a serialised map form in Yaml, stored in a file.
     *
     * @param yamlFile file in which the yaml is encoded in
     * @return the WillieChannelConfig object with the values defined in the Yaml
     * @throws IOException if there was an error reading the file
     */
    public static WillieChannelConfig fromYamlFile(File yamlFile) throws IOException {
        String yamlData = new String(Files.readAllBytes(Paths.get(yamlFile.toURI())));
        return WillieChannelConfig.fromYaml(yamlData);
    }
}
