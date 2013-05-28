package com.drtshock.willie;

import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.logging.Logger;

@SuppressWarnings("unchecked")
public class YamlHelper {
    private static final Logger logger = Logger.getLogger(YamlHelper.class.getName());
    private LinkedHashMap<String, Object> dataMap;
    private Yaml yaml = new Yaml();

    public YamlHelper(String filePath) throws FileNotFoundException {
        loadFile(filePath);
    }

    public YamlHelper(LinkedHashMap dataMap) {
        this.dataMap = dataMap;
    }

    public YamlHelper loadFile(String filePath) throws FileNotFoundException, ClassCastException {
        InputStream fileStream = new FileInputStream(filePath);
        dataMap = ((LinkedHashMap<String, Object>) yaml.load(fileStream));
        try {
            fileStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    public Object getObject(String path) throws ClassCastException {
        if (path.isEmpty()) return dataMap;
        LinkedHashMap<String, Object> currentMap = dataMap;
        String[] pathArray = path.split("\\.");
        for (int i = 0; i < pathArray.length; i++) {
            if (i == pathArray.length - 1) {
                return currentMap.get(pathArray[i]);
            } else currentMap = (LinkedHashMap) currentMap.get(pathArray[i]);
        }
        return null;
    }

    public <T> T getObject(Class<T> clazz, String path) throws ClassCastException {
        return (T) getObject(path);
    }

    public ArrayList<String> getKeys(String path) throws ClassCastException {
        ArrayList<String> keys = new ArrayList<>();

        ArrayList<String> pathList = new ArrayList<>();
        Collections.addAll(pathList, path.split("\\."));

        if (pathList.contains("")) {
            keys.addAll(dataMap.keySet());
        } else {
            LinkedHashMap<String, Object> currentMap = dataMap;
            for (String pathKey : path.split("\\.")) {
                currentMap = (LinkedHashMap) currentMap.get(pathKey);
            }
            if (currentMap != null) {
                keys.addAll(currentMap.keySet());
            }
        }
        return keys;
    }

    public String getString(String path) throws ClassCastException {
        String string = (String) getObject(path);
        return string != null ? string : "";
    }

    public ArrayList<String> getStringList(String path) throws ClassCastException {
        ArrayList<String> stringList = (ArrayList<String>) getObject(path);
        return stringList != null ? stringList : new ArrayList<String>();
    }

    public ArrayList<LinkedHashMap<String, Object>> getMapList(String path) throws ClassCastException {
        return (ArrayList<LinkedHashMap<String, Object>>) getObject(path);
    }

    public int getInt(String path) throws ClassCastException {
        return (int) getObject(path);
    }

    public double getDouble(String path) throws ClassCastException {
        return (double) getObject(path);
    }

    public float getFloat(String path) throws ClassCastException {
        return (float) getObject(path);
    }

    public LinkedHashMap<String, Object> getMap(String path) throws ClassCastException {
        return (LinkedHashMap<String, Object>) getObject(path);
    }

    public LinkedHashMap<String, Integer> getIntMap(String path) throws ClassCastException {
        return (LinkedHashMap<String, Integer>) getObject(path);
    }

    public LinkedHashMap<String, Double> getDoubleMap(String path) throws ClassCastException {
        return (LinkedHashMap<String, Double>) getObject(path);
    }
}
