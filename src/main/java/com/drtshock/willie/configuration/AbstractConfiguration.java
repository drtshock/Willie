package com.drtshock.willie.configuration;

import org.jsoup.helper.Validate;
import org.pircbotx.Channel;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class AbstractConfiguration {

    private static final Logger logger = Logger.getLogger(Configuration.class.getName());
    private LinkedHashMap<String, Object> configMap = new LinkedHashMap<>();
    private String fileName;
    private Channel channel;

    public AbstractConfiguration(String fileName) {
        this.fileName = fileName;
        loadFromFile();
        save();
    }

    public AbstractConfiguration(Channel channel) {
        this.channel = channel;
        this.fileName = channel.getName();
        loadFromFile();
        save();
    }

    /**
     * Loads the Configuration from file and puts configMap in memory.
     */
    private void loadFromFile() {
        YamlHelper yml = null;
        try {
            Validate.notNull(this.fileName, "File name cannot be null.");
            yml = new YamlHelper(fileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        for (Map.Entry<String, Object> entry : yml.getMap("").entrySet()) {
            configMap.put(entry.getKey(), entry.getValue());
        }
    }

    /**
     * Gets the config map.
     *
     * @return - the config map as a LinkedHashMap.
     */
    public LinkedHashMap<String, Object> getConfigMap() {
        return configMap;
    }

    public String fileName() {
        return this.fileName;
    }

    public Channel getChannel() {
        return this.channel;
    }

    /**
     * Gets an object from the configuration.
     *
     * @param path - path to the object.
     * @return - the object at the path.
     */
    public Object get(String path) {
        return configMap.get(path);
    }

    /**
     * Sets an object in the config at the specified path.
     * Automatically saves.
     *
     * @param path - the path as a string.
     * @param obj  - the object being set at the specified path.
     */
    public void set(String path, Object obj) {
        configMap.put(path, obj);
        save();
    }

    /**
     * Saves this instance of the file.
     */
    public void save() {
        try {
            Validate.notNull(this.fileName, "File name cannot be null.");
            File file = new File(this.fileName);
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
            try (PrintWriter printWriter = new PrintWriter(file)) {
                Yaml yml = new Yaml();
                printWriter.write(yml.dump(this.configMap));
            }
        } catch (Exception e) {
            logger.log(Level.WARNING, "Could not create configuration file at ''{0}''", this.fileName);
        }
    }
}
