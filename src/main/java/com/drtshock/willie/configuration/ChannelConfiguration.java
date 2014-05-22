package com.drtshock.willie.configuration;

import com.drtshock.willie.Willie;
import com.drtshock.willie.util.YamlHelper;
import org.pircbotx.Channel;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ChannelConfiguration {

    private static final Logger logger = Logger.getLogger(ChannelConfiguration.class.getName());
    private Map<String, Object> configMap = new LinkedHashMap<>();

    public ChannelConfiguration(Channel channel) {
        Willie.getInstance().getChannelConfigurationMap().put(channel.toString(), this);
        loadFromFile(channel.getName());
    }

    public void loadFromFile(String fileName) {
        Map<String, Object> config = getConfig();
        try {
            YamlHelper yml = new YamlHelper(fileName);

            for (Map.Entry<String, Object> entry : yml.getMap("").entrySet()) {
                config.put(entry.getKey(), entry.getValue());
            }

        } catch (FileNotFoundException e) {
            logger.log(Level.INFO, "Sorry, could not find configuration file at: {0}", fileName);
            logger.info("Saving default configuration...");
        }
        save(fileName);
    }

    public void save(String fileName) {
        try {

            File file = new File("channels" + File.separator + fileName);
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
            try (PrintWriter printWriter = new PrintWriter(file)) {
                Yaml yml = new Yaml();
                printWriter.write(yml.dump(configMap));
            }
        } catch (FileNotFoundException ignored) {
            logger.log(Level.WARNING, "Could not create configuration file at ''{0}''", fileName);
        } catch (IOException e) {
            logger.log(Level.WARNING, "Could not write configuration to file ''{0}''", fileName);
        }
    }

    // TODO: add per channel stuff that is currently in WillieConfig

    /**
     * Get the configuration object.
     *
     * @return ChannelConfiguration map.
     */
    public Map<String, Object> getConfig() {
        return configMap;
    }
}
