package com.drtshock.willie;

import com.drtshock.willie.util.YamlHelper;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@SuppressWarnings("unchecked")
public class WillieConfig {

    private static final Logger logger = Logger.getLogger(WillieConfig.class.getName());
    private LinkedHashMap<String, Object> configMap = new LinkedHashMap<>();
    private ArrayList<String> botAdmins = new ArrayList<>();
    private ArrayList<String> botChannels = new ArrayList<>();
    private ArrayList<String> logChannelBlacklist = new ArrayList<>();
    ArrayList<String> jenkinsAdmins = new ArrayList<>();

    public WillieConfig() {
        // Default configuration
        botAdmins.add("drtshock");
        botChannels.add("#drtshock");

        jenkinsAdmins.add("drtshock");
        jenkinsAdmins.add("blha303");

        configMap.put("github-api-key", "change-me");
        configMap.put("jenkins-server", "http://ci.drtshock.com/");
        configMap.put("jenkins-admins", jenkinsAdmins);
        configMap.put("bot-source-url", "https://github.com/drtshock/willie");
        configMap.put("donate-url", "http://tinyurl.com/drtdonate");
        configMap.put("bot-admins", botAdmins);
        configMap.put("bot-nick", "Willie");
        configMap.put("account-pass", "");
        configMap.put("server", "irc.esper.net");
        configMap.put("server-pass", "");
        configMap.put("server-port", 6667);
        configMap.put("channels", botChannels);
        configMap.put("command-prefix", "!");
        configMap.put("log-enabled", false);
        configMap.put("log-channel-blacklist", logChannelBlacklist);
        configMap.put("log-directory", "./logs");
    }

    public LinkedHashMap<String, Object> getConfigMap() {
        return configMap;
    }

    public WillieConfig update() {
        botChannels = (ArrayList<String>) configMap.get("channels");
        botAdmins = (ArrayList<String>) configMap.get("bot-admins");
        jenkinsAdmins = (ArrayList<String>) configMap.get("jenkins-admins");
        logChannelBlacklist = (ArrayList<String>) configMap.get("log-channel-blacklist");

        // Strip trailing slash from log-directory
        String logDirectory = (String) configMap.get("log-directory");
        if(logDirectory.endsWith("/")) {
            logDirectory = logDirectory.substring(1, logDirectory.length()-1);
            configMap.put("log-directory", logDirectory);
        }
        return this;
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
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
        } catch (FileNotFoundException ignored) {
            logger.log(Level.WARNING, "Could not create configuration file at ''{0}''", fileName);
        } catch (IOException e) {
            logger.log(Level.WARNING, "Could not write configuration to file ''{0}''", fileName);
        }
    }

    public static WillieConfig loadFromFile(String fileName) {
        WillieConfig willieConfig = new WillieConfig();
        LinkedHashMap<String, Object> config = willieConfig.getConfigMap();
        try {
            YamlHelper yml = new YamlHelper(fileName);

            for (Map.Entry<String, Object> entry : yml.getMap("").entrySet()) {
                config.put(entry.getKey(), entry.getValue());
            }
            willieConfig.update();

        } catch (FileNotFoundException e) {
            logger.log(Level.INFO, "Sorry, could not find configuration file at: {0}", fileName);
            logger.info("Saving default configuration...");
        }
        willieConfig.save(fileName);
        return willieConfig;
    }

    public String getGitHubApiKey() {
        return (String) configMap.get("github-api-key");
    }

    public String getJenkinsServer() {
        return (String) configMap.get("jenkins-server");
    }

    public boolean isLoggingEnabled() {
        return (Boolean) configMap.get("log-enabled");
    }

    public boolean isChannelLogged(String channel) {
        return isLoggingEnabled() && logChannelBlacklist.contains(channel);
    }

    public ArrayList<String> getLogBlacklist() {
        return logChannelBlacklist;
    }

    public String getLoggingDirectory() {
        return (String) configMap.get("log-directory");
    }

    public WillieConfig setJenkinsServer(String server) {
        configMap.put("jenkins-server", server);
        return this;
    }

    public ArrayList<String> getAdmins() {
        return botAdmins;
    }

    public WillieConfig addAdmin(String name) {
        botAdmins.add(name);
        return this;
    }

    public WillieConfig removeAdmin(String name) {
        botAdmins.remove(name);
        return this;
    }

    public WillieConfig setAdmins(String... admins) {
        botAdmins.clear();
        Collections.addAll(botAdmins, admins);
        return this;
    }

    public String getNick() {
        return (String) configMap.get("bot-nick");
    }

    public WillieConfig setNick(String botNick) {
        configMap.put("bot-nick", botNick);
        return this;
    }

    public String getAccountPass() {
        return (String) configMap.get("account-pass");
    }

    public WillieConfig setAccountPass(String pass) {
        configMap.put("account-pass", pass);
        return this;
    }

    public String getServerPass() {
        return (String) configMap.get("server-pass");
    }

    public String getServer() {
        return (String) configMap.get("server");
    }

    public int getServerPort() {
        return (int) configMap.get("server-port");
    }

    public WillieConfig setServer(String server) {
        configMap.put("server", server);
        return this;
    }

    public ArrayList<String> getChannels() {
        return botChannels;
    }

    public WillieConfig addChannel(String channel) {
        botChannels.add(channel);
        return this;
    }

    public WillieConfig removeChannel(String channel) {
        botChannels.remove(channel);
        return this;
    }

    public WillieConfig setChannels(String... channels) {
        botChannels.clear();
        Collections.addAll(botChannels, channels);
        return this;
    }

    public String getCommandPrefix() {
        return (String) configMap.get("command-prefix");
    }

    public WillieConfig setCommandPrefix(String prefix) {
        configMap.put("command-prefix", prefix);
        return this;
    }

    public String getBotSourceUrl() {
        return (String) configMap.get("bot-source-url");
    }

    public WillieConfig setBotSourceUrl(String url) {
        configMap.put("bot-source-url", url);
        return this;
    }

    public String getDonateUrl() {
        return (String) configMap.get("donate-url");
    }

    public WillieConfig setDonateUrl(String donateUrl) {
        configMap.put("donate-url", donateUrl);
        return this;
    }

    public ArrayList<String> getJenkinsAdmins() {
        return jenkinsAdmins;
    }
}
