package com.drtshock.willie.command;

public class Command {

    private String name;
    private String help;
    private ICommand handler;
    private boolean adminOnly;

    public Command(String name, String help, ICommand handler, boolean adminOnly) {
        this.name = name;
        this.help = help;
        this.handler = handler;
        this.adminOnly = adminOnly;
    }

    public String getName() {
        return this.name;
    }

    public String getHelp() {
        return this.help;
    }

    public ICommand getHandler() {
        return this.handler;
    }

    public boolean isAdminOnly() {
        return this.isAdminOnly();
    }

}
