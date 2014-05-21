package com.drtshock.willie.command.utility;

import com.drtshock.willie.Willie;
import com.drtshock.willie.command.CommandHandler;
import com.drtshock.willie.util.WebHelper;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.pircbotx.Channel;
import org.pircbotx.User;

import java.net.URL;
import java.net.URLEncoder;

public class WolframCommandHandler implements CommandHandler {

    private static final String API_URL = "http://api.wolframalpha.com/v2/query?format=plaintext&appid={KEY}&input={QUERY}",
            QUERY_URL = "http://www.wolframalpha.com/input/?i=";

    private SAXBuilder builder = new SAXBuilder();

    @Override
    public void handle(Willie bot, Channel channel, User sender, String[] args) throws Exception {
        if (args.length == 0) {
            channel.sendMessage("Usage: wa <query>");
        } else {
            String key = bot.getConfig().getWolframApiKey();
            System.out.println("Key: " + key);

            StringBuilder argsBuilder = new StringBuilder();

            for (String arg : args) {
                argsBuilder.append(arg);

                if (arg != args[args.length - 1]) {
                    argsBuilder.append(" ");
                }
            }

            String input = URLEncoder.encode(argsBuilder.toString(), "UTF-8");

            Document document = builder.build(new URL(API_URL.replace("{KEY}", key).replace("{QUERY}", input)));

            StringBuilder answer = new StringBuilder();

            Element root = document.getRootElement();

            if (root.getAttribute("success").getBooleanValue()) {
                for (Element pod : root.getChildren("pod")) {
                    if (pod.getAttribute("primary") != null && pod.getAttribute("primary").getBooleanValue()) {
                        for (Element subPod : pod.getChildren("subpod")) {
                            String content = subPod.getChild("plaintext").getText().replaceAll("\\n", "; ").replaceAll("\\s+", " ");

                            if (!content.isEmpty()) {
                                answer.append(content);
                            }
                        }
                    }
                }

                channel.sendMessage(answer + " - " + WebHelper.shortenURL(QUERY_URL + input));
            } else if(root.getAttribute("error").getBooleanValue()) {
                channel.sendMessage("Query failed - " + root.getChild("error").getChild("msg").getText());
            } else {
				channel.sendMessage("Wolfram Alpha doesn't know how to interpret that!");
			}
        }
    }
}
