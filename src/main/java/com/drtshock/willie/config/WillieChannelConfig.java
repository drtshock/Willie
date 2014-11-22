package com.drtshock.willie.config;

import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public class WillieChannelConfig {
    // TODO: create generic YamlEnumMapConfig to prevent code repetition between WillieConfig and WillieChannelConfig

    private final EnumMap<WillieChannelConfigOption, Object> configMap = new EnumMap<>(WillieChannelConfigOption.class);

    public Object getOptionValue(WillieChannelConfigOption option) {
        if (configMap.containsKey(option)) {
            return configMap.get(option);
        }

        return option.getDefaultValue();
    }

    public void putOptionValue(WillieChannelConfigOption option, Object value) {
        configMap.put(option, value);
    }

    public String toYaml() {
        Map<String, Object> valueMap = new HashMap<>();

        for (Map.Entry<WillieChannelConfigOption, Object> entry : configMap.entrySet()) {
            valueMap.put(entry.getKey().getOptionNode(), entry.getValue());
        }

        Yaml yaml = new Yaml();
        return yaml.dump(valueMap);
    }

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

    public static WillieChannelConfig fromYamlFile(File yamlFile) throws IOException {
        String yamlData = new String(Files.readAllBytes(Paths.get(yamlFile.toURI())));
        return WillieChannelConfig.fromYaml(yamlData);
    }
}
