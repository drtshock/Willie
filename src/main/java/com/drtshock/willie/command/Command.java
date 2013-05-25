package com.drtshock.willie.command;

public class Command {
	
	private String name;
	private String help;
	private CommandHandler handler;
	
	public Command(String name, String help, CommandHandler handler){
		this.name = name;
		this.help = help;
		this.handler = handler;
	}
	
	public String getName(){
		return this.name;
	}
	
	public String getHelp(){
		return this.help;
	}
	
	public CommandHandler getHandler(){
		return this.handler;
	}
	
}
