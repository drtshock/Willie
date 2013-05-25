package com.drtshock.willie;

import java.util.Collection;
import java.util.HashMap;

import org.pircbotx.Channel;
import org.pircbotx.hooks.Listener;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;

import com.drtshock.willie.command.Command;

public class CommandManager extends ListenerAdapter<Willie> implements Listener<Willie> {
	
	private Willie bot;
	private HashMap<String, Command> commands;
	
	public CommandManager(Willie bot){
		this.bot = bot;
		this.commands = new HashMap<String, Command>();
	}
	
	public void registerCommand(Command command){
		this.commands.put(command.getName(), command);
	}
	
	public Collection<Command> getCommands(){
		return this.commands.values();
	}
	
	@Override
	public void onMessage(MessageEvent<Willie> event){
		String message = event.getMessage();
		
		if (message.isEmpty() || message.charAt(0) != '!'){
			return;
		}
		
		String[] parts = message.substring(1).split(" ");
		Channel channel = event.getChannel();
		
		String commandName = parts[0].toLowerCase();
		String[] args = new String[parts.length - 1];
		System.arraycopy(parts, 1, args, 0, args.length);
		
		Command command = this.commands.get(commandName);
		
		if (command != null){
			command.getHandler().handle(this.bot, channel, event.getUser(), args);
		}
	}
	
}
