package com.drtshock.willie.command.utility;

import com.drtshock.willie.Willie;
import com.drtshock.willie.command.CommandHandler;
import com.drtshock.willie.pastebin.Pastebin;
import org.pircbotx.Channel;
import org.pircbotx.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Pattern;

public class SourceCommandHandler implements CommandHandler {

	@Override
	public void handle(Willie bot, Channel channel, User sender, String[] args) {
		if (args.length == 0) {
			channel.sendMessage("(" + sender.getNick() + ") Usage: !source <url>");
		} else {
			try {
				channel.sendMessage("(" + sender.getNick() + ") " + Pastebin.makePaste(getPage(args[0]), args[0], "html5"));
			} catch (IOException ex) {
				channel.sendMessage("(" + sender.getNick() + ") Invalid URL");
			}
		}
	}

	private String getPage(String urlString) throws IOException {
		URL url = new URL(urlString);

		HttpURLConnection connection = (HttpURLConnection) url.openConnection();

		connection.setConnectTimeout(10000);
		connection.setReadTimeout(10000);
		connection.setUseCaches(false);
		String page;
		try (BufferedReader input = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
			final StringBuilder buffer = new StringBuilder();
			String line;
			while ((line = input.readLine()) != null) {
				buffer.append(line).append("\r\n");
			}

			page = buffer.toString().replaceAll(Pattern.quote("\t"), "  ");
		}

		return page;
	}

}
