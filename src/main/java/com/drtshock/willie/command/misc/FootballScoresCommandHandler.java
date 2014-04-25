package com.drtshock.willie.command.misc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.pircbotx.Channel;
import org.pircbotx.User;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.drtshock.willie.Willie;
import com.drtshock.willie.command.CommandHandler;

public class FootballScoresCommandHandler implements CommandHandler {

	private List<String> todaysScores;

	public static void main(String[] args) {
		new FootballScoresCommandHandler();
	}

	public FootballScoresCommandHandler() {
		todaysScores = new ArrayList<String>();

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse("http://www.scorespro.com/rss2/live-soccer.xml");
			
			doc.normalize();

			NodeList results = doc.getElementsByTagName("item");

			for (int i = 0; i < results.getLength(); i++) {

				Node n = results.item(i);

				String title = n.getTextContent();

				if (title.startsWith("Soccer") && title.contains("Game Finished")) {
					String raw = title.substring(title.indexOf("#"), title.lastIndexOf("Game Finished"));
					String clean = raw.replaceAll("#", "");
					todaysScores.add(clean);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void handle(Willie bot, Channel channel, User sender, String[] args) throws Exception {
		sender.sendMessage("Today's scores:");
		for (String s : todaysScores) {
			sender.sendMessage(s);
		}
	}

}
