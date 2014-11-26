package com.drtshock.willie.feature;

import com.drtshock.willie.Willie;
import org.kitteh.irc.EventHandler;
import org.kitteh.irc.elements.User;
import org.kitteh.irc.event.channel.ChannelJoinEvent;
import org.kitteh.irc.event.channel.ChannelMessageEvent;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Nick Parks on 11/25/2014 at 3:00 PM.
 */

public class QuestionResponse {
    public static final String FEATURE_NAME = "question-response";
    public static final String QUESTION_RESPONSE = "%s , if you have asked your question please wait patiently for a response :)";

    HashMap<String, List<String>> hasSpoken = new HashMap<>();

    private final Willie willie;

    public QuestionResponse(Willie willie){
        this.willie = willie;
    }

    @EventHandler
    public void onChannelJoin(ChannelJoinEvent event){
        if(willie.isModuleEnabled(event.getChannel().getName(), FEATURE_NAME)) {
            if (!hasSpoken.containsKey(event.getChannel().getName())) {
                List useMe = Arrays.asList("Willie");
                hasSpoken.put(event.getChannel().getName(), useMe);
            }
        }
    }

    @EventHandler
    public void onChannelMessage(ChannelMessageEvent channelMessageEvent){
        String channelName = channelMessageEvent.getChannel().getName();
        if(willie.isModuleEnabled(channelName, FEATURE_NAME)) {
            String message = channelMessageEvent.getMessage();
            if(!message.endsWith("?")) {
                return;
            }
            String username = channelMessageEvent.getActor().getName();
            User nick = (User) channelMessageEvent.getActor();
            String nickName = nick.getNick();
            if(!hasSpoken.get(channelName).contains(username)){
                String sendMe = QUESTION_RESPONSE.replace("%s", nickName);
                willie.getBot().sendMessage(channelName, sendMe);
                List list = hasSpoken.get(channelName);
                list.add(username);
                hasSpoken.put(channelName, list);
            }
        }
    }
}
