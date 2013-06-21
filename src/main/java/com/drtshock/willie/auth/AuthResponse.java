package com.drtshock.willie.auth;

import com.drtshock.willie.Willie;
import org.pircbotx.User;
import org.pircbotx.hooks.events.NoticeEvent;

public class AuthResponse {
    public boolean isValid = false;
    public boolean isLoggedIn = false;
    public boolean isAdmin = false;
    public String accountName = "";

    protected AuthResponse(Willie willie, NoticeEvent event, User user) {
        if (!event.getUser().getNick().equals("NickServ")) return;
        String[] args = event.getNotice().split(" ");
        if (args.length < 5) return;
        if (!args[0].equals(user.getNick())) return;

        this.isValid = true;
        this.accountName = args[2];
        if (Integer.parseInt(args[4]) == 3) this.isLoggedIn = true;
        if (this.isLoggedIn && willie.getConfig().getAdmins().contains(accountName)) {
            this.isAdmin = true;
        }
    }

    protected AuthResponse() {
    }

    public boolean isValid() {
        return isValid;
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public String getAccountName() {
        return accountName;
    }
}
