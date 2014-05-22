package com.drtshock.willie.configuration;

import com.drtshock.willie.util.YamlHelper;
import org.pircbotx.Channel;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@SuppressWarnings("unchecked")
public class WillieConfig {

    private static final Logger logger = Logger.getLogger(WillieConfig.class.getName());
    private Map<String, Object> configMap = new LinkedHashMap<>();
	private List<String> gitlabChannels = new ArrayList<>();
    private List<String> botChannels = new ArrayList<>();
    private List<String> jenkinsAdmins = new ArrayList<>();
    private List<String> blacklistedWords = new ArrayList<>();
    private Map<String, List<String>> ignoredChannels = new HashMap<String, List<String>>();

    public WillieConfig() {
        // Default configuration
        botChannels.add("#willie");

        jenkinsAdmins.add("drtshock");
        jenkinsAdmins.add("blha303");

        ignoredChannels.put("#hawkfalcon", Arrays.asList("p", "w"));

		gitlabChannels.add("#puzldevs");

        configMap.put("github-api-key", "change-me");
        configMap.put("wolfram-api-key", "change-me");
        configMap.put("pastebin-api-key", "change-me");
        configMap.put("pastebin-username", "change-me");
        configMap.put("pastebin-password", "change-me");
        configMap.put("jenkins-server", "http://ci.drtshock.net/");
        configMap.put("jenkins-admins", jenkinsAdmins);
        configMap.put("bot-source-url", "https://github.com/drtshock/willie");
        configMap.put("donate-url", "http://tinyurl.com/drtdonate");
        configMap.put("bot-nick", "Willie");
        configMap.put("account-pass", "");
        configMap.put("server-pass", "");
        configMap.put("server", "drtshock.com");
        configMap.put("port", 5555);
		configMap.put("http-port", 8080);
        configMap.put("channels", botChannels);
		configMap.put("gitlab-channels", gitlabChannels);
        configMap.put("command-prefix", "!");
        configMap.put("twitter-consumer-key", "change-me");
        configMap.put("twitter-consumer-key-secret", "change-me");
        configMap.put("twitter-access-token", "change-me");
        configMap.put("twitter-access-token-secret", "change-me");
        configMap.put("blacklisted-words", blacklistedWords);
        configMap.put("ignored-channels", ignoredChannels);
    }

    public Map<String, Object> getConfigMap() {
        return configMap;
    }

    public WillieConfig update() {
        botChannels = (List<String>) configMap.get("channels");
        jenkinsAdmins = (List<String>) configMap.get("jenkins-admins");
        blacklistedWords = (List<String>) configMap.get("blacklisted-words");
        ignoredChannels = (Map<String, List<String>>) configMap.get("ignored-channels");
		gitlabChannels = (List<String>) configMap.get("gitlab-channels");
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
        } catch (FileNotFoundException ignored) {
            logger.log(Level.WARNING, "Could not create configuration file at ''{0}''", fileName);
        } catch (IOException e) {
            logger.log(Level.WARNING, "Could not write configuration to file ''{0}''", fileName);
        }
    }

    public static WillieConfig loadFromFile(String fileName) {
        WillieConfig willieConfig = new WillieConfig();
        Map<String, Object> config = willieConfig.getConfigMap();
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

    public String getWolframApiKey() {
        return (String) configMap.get("wolfram-api-key");
    }

    public String getPastebinApiKey() {
        return (String) configMap.get("pastebin-api-key");
    }

    public String getPastebinUsername() {
        return (String) configMap.get("pastebin-username");
    }

    public String getPastebinPassword() {
        return (String) configMap.get("pastebin-password");
    }

    public String getJoinMessage(Channel c) {
        return (String) configMap.get(c.toString());
    }

    public void setJoinMessage(Channel c, String s) {
        configMap.put(c.toString(), s);
    }

    public boolean hasJoinMessage(Channel c) {
        return configMap.containsKey(c.toString()) && !(configMap.get(c.toString()).equals(""));
    }

    public String getJenkinsServer() {
        return (String) configMap.get("jenkins-server");
    }

    public WillieConfig setJenkinsServer(String server) {
        configMap.put("jenkins-server", server);
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

    public String getServer() {
        return (String) configMap.get("server");
    }

    public int getPort() {
        return (int) configMap.get("port");
    }

    public WillieConfig setPort(int port) {
        configMap.put("port", port);
        return this;
    }

	public int getHttpPort() {
		return (int) configMap.get("http-port");
	}

	public WillieConfig setHttpPort(int port) {
		configMap.put("http-port", port);
		return this;
	}

    public String getPassword() {
        return (String) configMap.get("server-pass");
    }

    public WillieConfig setServerPass(String pass) {
        configMap.put("server-pass", pass);
        return this;
    }

    public WillieConfig setServer(String server) {
        configMap.put("server", server);
        return this;
    }

    public List<String> getChannels() {
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

    public List<String> getJenkinsAdmins() {
        return jenkinsAdmins;
    }

    public List<String> getBlacklistedWords() {
        return blacklistedWords;
    }

    public boolean isBlacklisted(String string) {
        for (String s : string.split(" ")) {
            if (blacklistedWords.contains(s)) return true;
        }
        return false;
    }

    public boolean blacklistWord(String s) {
        if (blacklistedWords.contains(s)) {
            blacklistedWords.remove(s);
            return false;
        } else {
            blacklistedWords.add(s);
            return true;
        }
    }

    public boolean addIgnoredChannel(String command, String channel) {
        List<String> currentChannels = this.getIgnoredChannels(command);
        if (!currentChannels.contains(channel.toLowerCase())) {
            currentChannels.add(channel.toLowerCase());
            return true;
        } else {
            return false;
        }
    }

    public boolean isIgnoredChannel(String channel, String command) {
        return this.getIgnoredChannels(command).contains(channel.toLowerCase());
    }

    public Map<String, List<String>> getIgnoredChannels() {
        return this.ignoredChannels;
    }

    public List<String> getIgnoredChannels(String command) {
        return this.ignoredChannels.containsKey(command.toLowerCase()) ? this.ignoredChannels.get(command.toLowerCase()) : new ArrayList<String>();
    }

	public List<String> getGitlabChannels() {
		return gitlabChannels;
	}

    public boolean removeIgnoredChannel(String channel, String command) {
        List<String> currentChannels = this.getIgnoredChannels(command);
        if (currentChannels.contains(channel.toLowerCase())) {
            currentChannels.remove(channel.toLowerCase());
            return true;
        } else {
            return false;
        }
    }

    public String getTwitterConsumerKey() {
        return (String) configMap.get("twitter-consumer-key");
    }

    public String getTwitterConsumerKeySecret() {
        return (String) configMap.get("twitter-consumer-key-secret");
    }

    public String getTwitterAccessToken() {
        return (String) configMap.get("twitter-access-token");
    }

    public String getTwitterAccessTokenSecret() {
        return (String) configMap.get("twitter-access-token-secret");
    }
}
