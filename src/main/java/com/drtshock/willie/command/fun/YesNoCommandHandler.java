package com.drtshock.willie.command.fun;

import com.drtshock.willie.Willie;
import com.drtshock.willie.command.CommandHandler;
import org.pircbotx.Channel;
import org.pircbotx.User;

import java.util.List;
import java.util.Random;

/**
 * @author ParaPenguin
 */
public class YesNoCommandHandler implements CommandHandler {

    @Override
    public void handle(Willie bot, Channel channel, User sender, String[] args) {
        boolean rand = new Random().nextBoolean();

        String question;
        if (args.length == 0) {
            String[] questions = new String[]{"Is the world flat?", "Are sheep blue?", "Can pigs fly?"};
            question = questions[getRandom(0, questions.length)];
        } else {
            StringBuilder builder = new StringBuilder();
            for (String arg : args) {
                builder.append(arg).append(" ");
            }
            question = builder.toString();
        }

        bot.sendAction(channel, question + (question.endsWith("?") ? "" : "?") + " " + rand);
    }

    private int getRandom(int min, int max) {
        return new Random().nextInt((max - min) + 1) + min;
    }

}
