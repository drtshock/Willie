package com.drtshock.willie.command.fun;

import com.drtshock.willie.Willie;
import com.drtshock.willie.command.CommandHandler;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.pircbotx.Channel;
import org.pircbotx.User;

import java.io.IOException;

public class AcronymCommandHandler implements CommandHandler {

    @Override
    public void handle(Willie bot, Channel channel, User sender, String[] args) {
        if (args.length != 1) {
            channel.sendMessage("Enter a single acronym!");
            return;
        }
        Document document = null;
        try {
            document = Jsoup.connect("http://www.acronymfinder.com/" + args[0] + ".html").get();
        } catch (IOException ex) {
            ex.printStackTrace();
            return;
        }
        final Elements definitions = document.getElementsByClass("result-list__body__meaning");
        final String[] output = new String[3];
        for (int i = 0; i < (definitions.size() > 3 ? 3 : definitions.size()); i++) {
            output[i] = definitions.get(i).text();
        }
        if (output.length == 0) {
            channel.sendMessage("No results found.");
            return;
        }
        channel.sendMessage("Possible definition" + (output.length > 1 ? "s" : "") + ":");
        for (String str : output) {
            channel.sendMessage("- " + str);
        }
    }

}
