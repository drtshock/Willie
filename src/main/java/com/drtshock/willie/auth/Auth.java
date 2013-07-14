package com.drtshock.willie.auth;

import org.pircbotx.User;
import org.pircbotx.hooks.WaitForQueue;
import org.pircbotx.hooks.events.NoticeEvent;

import com.drtshock.willie.Willie;

public class Auth{
	public static AuthResponse checkAuth(User user){
		Willie willie = Willie.getInstance();

		willie.getUser("NickServ").sendMessage(String.format("ACC %s *", user.getNick()));
		WaitForQueue queue = new WaitForQueue(willie);
		try{
			NoticeEvent<?> event = queue.waitFor(NoticeEvent.class);
			return new AuthResponse(willie, event, user);
		}catch(Exception ignored){}
		return new AuthResponse();
	}
}
