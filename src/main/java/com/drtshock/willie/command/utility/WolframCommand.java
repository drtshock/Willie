package com.drtshock.willie.command.utility;

import com.drtshock.willie.Willie;
import com.drtshock.willie.command.CommandHandler;
import com.drtshock.willie.util.WebHelper;
import javafx.scene.paint.Color;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.pircbotx.Channel;
import org.pircbotx.User;

import java.net.URL;
import java.net.URLEncoder;

public class WolframCommand implements CommandHandler {

    private static final String API_URL = "http://api.wolframalpha.com/v2/query?format=plaintext&appid=%s&input=%s",
    QUERY_URL = "http://www.wolframalpha.com/input/?i=%s";

    private SAXBuilder builder = new SAXBuilder();

    @Override
    public void handle(Willie bot, Channel channel, User sender, String[] args) throws Exception {
        if(args.length == 0) {
            channel.sendMessage(Color.RED + "Usage: wa <query>");
        } else {
            String key = bot.getConfig().getWolframApiKey();

            StringBuilder argsBuilder = new StringBuilder();

            for(String arg : args) {
                argsBuilder.append(arg);

                if(arg != args[args.length - 1]) {
                    argsBuilder.append(" ");
                }
            }

            String input = URLEncoder.encode(argsBuilder.toString(), "UTF-8");

            Document document = builder.build(new URL(String.format(API_URL, key, input)));

            StringBuilder answer = new StringBuilder();

            Element root = document.getRootElement();

            if(root.getAttribute("success").getBooleanValue()) {
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

                channel.sendMessage(String.format("%s - %s", answer, WebHelper.shortenURL(String.format(QUERY_URL, input))));
            } else {
                throw new IllegalStateException("Query failed - " + root.getChild("error").getChild("msg").getText());
            }
        }
    }
}
