package com.drtshock.willie.configuration;

import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Configuration {

    private static final Logger logger = Logger.getLogger(Configuration.class.getName());
    private LinkedHashMap<String, Object> configMap = new LinkedHashMap<>();
    private List<String> admins = new ArrayList<>();
    private List<String> channels = new ArrayList<>();

    public Configuration() {
        admins.add("drtshock");
    }

    public LinkedHashMap<String, Object> getConfigMap() {
        return configMap;
    }

    public Configuration update() {
        admins = (ArrayList<String>) configMap.get("admins");
        channels = (ArrayList<String>) configMap.get("channels");
        return this;
    }

    public void save(String fileName) {
        update();

        try {
            File file = new File(fileName);
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
            try (PrintWriter printWriter = new PrintWriter(file)) {
                Yaml yml = new Yaml();
                printWriter.write(yml.dump(configMap));
            }
        } catch (Exception e) {
            logger.log(Level.WARNING, "Could not create configuration file at ''{0}''", fileName);
        }
    }
}
