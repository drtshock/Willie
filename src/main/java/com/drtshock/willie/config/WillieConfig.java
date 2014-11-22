package com.drtshock.willie.config;

import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public class WillieConfig {
    private final EnumMap<WillieConfigOption, Object> configMap = new EnumMap<>(WillieConfigOption.class);
    private final HashMap<String, WillieChannelConfig> channelConfigsMap = new HashMap<>();

    public Object getOptionValue(WillieConfigOption option) {
        if (configMap.containsKey(option)) {
            return configMap.get(option);
        }

        return option.getDefaultValue();
    }

    public void putOptionValue(WillieConfigOption option, Object value) {
        configMap.put(option, value);
    }

    public WillieChannelConfig getChannelConfig(String channelName) {
        if (channelConfigsMap.containsKey(channelName)) {
            return channelConfigsMap.get(channelName);
        }

        return new WillieChannelConfig();
    }

    public void putChannelConfig(String channelName, WillieChannelConfig channelConfig) {
        channelConfigsMap.put(channelName, channelConfig);
    }

    public String toYaml() {
        Map<String, Object> valueMap = new HashMap<>();

        for (Map.Entry<WillieConfigOption, Object> entry : configMap.entrySet()) {
            valueMap.put(entry.getKey().getOptionNode(), entry.getValue());
        }

        Yaml yaml = new Yaml();
        return yaml.dump(valueMap);
    }

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

    public static WillieConfig fromYamlFile(File yamlFile) throws IOException {
        String yamlData = new String(Files.readAllBytes(Paths.get(yamlFile.toURI())));
        return WillieConfig.fromYaml(yamlData);
    }
}
