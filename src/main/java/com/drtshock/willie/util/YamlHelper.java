package com.drtshock.willie.util;

import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@SuppressWarnings("unchecked")
public final class YamlHelper {

    private Map<String, Object> dataMap;
    private Yaml yaml = new Yaml();

    public YamlHelper(String filePath) throws FileNotFoundException {
        loadFile(filePath);
    }

    public YamlHelper(Map<String, Object> dataMap) {
        this.dataMap = dataMap;
    }

    public YamlHelper loadFile(String filePath) throws FileNotFoundException, ClassCastException {
        InputStream fileStream = new FileInputStream(filePath);
        dataMap = ((Map<String, Object>) yaml.load(fileStream));
        try {
            fileStream.close();
        } catch (IOException e) {
        }
        return this;
    }

    public Object getObject(String path) throws ClassCastException {
        if (path.isEmpty()) {
            return dataMap;
        }
        Map<String, Object> currentMap = dataMap;
        String[] pathArray = path.split("\\.");
        for (int i = 0; i < pathArray.length; i++) {
            if (i == pathArray.length - 1) {
                return currentMap.get(pathArray[i]);
            } else {
                currentMap = (Map<String, Object>) currentMap.get(pathArray[i]);
            }
        }
        return null;
    }

    public <T> T getObject(Class<T> clazz, String path) throws ClassCastException {
        return (T) getObject(path);
    }

    public ArrayList<String> getKeys(String path) throws ClassCastException {
        ArrayList<String> keys = new ArrayList<>();

        List<String> pathList = new ArrayList<>();
        Collections.addAll(pathList, path.split("\\."));

        if (pathList.contains("")) {
            keys.addAll(dataMap.keySet());
        } else {
            Map<String, Object> currentMap = dataMap;
            for (String pathKey : path.split("\\.")) {
                currentMap = (Map<String, Object>) currentMap.get(pathKey);
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

    public Map<String, Object> getMap(String path) throws ClassCastException {
        return (Map<String, Object>) getObject(path);
    }

    public Map<String, Integer> getIntMap(String path) throws ClassCastException {
        return (Map<String, Integer>) getObject(path);
    }

    public Map<String, Double> getDoubleMap(String path) throws ClassCastException {
        return (Map<String, Double>) getObject(path);
    }
}
