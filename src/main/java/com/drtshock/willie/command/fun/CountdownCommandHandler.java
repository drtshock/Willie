package com.drtshock.willie.command.fun;

import com.drtshock.willie.Willie;
import com.drtshock.willie.command.CommandHandler;
import org.pircbotx.Channel;
import org.pircbotx.User;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

public class CountdownCommandHandler implements CommandHandler {

	private final HashSet<Timer> timers = new HashSet<>();

	@Override
	public void handle(Willie bot, Channel channel, User sender, String[] args) {
		if (args.length == 0) {
			msgUser(channel, sender, "Usage: !countdown <time[d, h, m, s>]|stop|left>");
		} else if (args[0].equalsIgnoreCase("stop")) {
			final Iterator<Timer> itr = timers.iterator();
			while (itr.hasNext()) {
				final Timer timer = itr.next();

				if (timer.getUser().equals(sender)) {
					timer.cancel();
					itr.remove();
					return;
				}
			}

			msgUser(channel, sender, "You don't have a timer");
		} else if (args[0].equalsIgnoreCase("left")) {
			for (Timer timer : timers) {
				if (timer.getUser().equals(sender)) {
					msgUser(channel, sender, "Time left: " + timer.getTime());
					return;
				}
			}

			msgUser(channel, sender, "You don't have a timer");
		} else {
			for (Timer timer : timers) {
				if (timer.getUser().equals(sender)) {
					msgUser(channel, sender, "You already have a timer running");
					return;
				}
			}

			HashMap<TimeUnit, Integer> times = new HashMap<>();

			for (String arg : args) {
				final StringBuilder num = new StringBuilder();
				char unit = ' ';

				for (char c : arg.toCharArray()) {
					if (Character.isDigit(c)) {
						num.append(c);
					} else if (Character.isLetter(c)) {
						unit = c;
						break;
					}
				}

				final String numString = num.toString();
				if (numString.isEmpty()) {
					msgUser(channel, sender, "Invalid time");
					return;
				}

				times.put(TimeUnit.byFirstLetter(unit), Integer.valueOf(numString));
			}

			long time = 0;
			for (HashMap.Entry<TimeUnit, Integer> entry : times.entrySet()) {
				TimeUnit key = entry.getKey();

				if (key == null) {
					msgUser(channel, sender, "Invalid time");
					return;
				}

				time += (key.seconds * entry.getValue());
			}

			final Timer timer = new Timer(time, channel, sender);
			timers.add(timer);
			msgUser(channel, sender, "Created timer for " + timer.getTime());
		}
	}

	private enum TimeUnit {

		SECOND(1),
		MINUTE(SECOND.seconds * 60),
		HOUR(MINUTE.seconds * 60),
		DAY(HOUR.seconds * 24);

		private final long seconds;

		private TimeUnit(long seconds) {
			this.seconds = seconds;
		}

		public static TimeUnit byFirstLetter(char c) {
			for (TimeUnit timeUnit : TimeUnit.values()) {
				if (timeUnit.name().charAt(0) == Character.toUpperCase(c)) {
					return timeUnit;
				}
			}

			return null;
		}

	}

	private class Timer extends Thread {

		private long time;
		private Channel channel;
		private User user;

		private boolean stopped = false;

		public Timer(long time, Channel channel, User user) {
			this.time = time;
			this.channel = channel;
			this.user = user;
			start();
		}

		@Override
		public void run() {
			while (time > 0) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException ex) {
					ex.printStackTrace();
					msgUser(channel, user, "Timer forcibly stopped");
					removeThis();
					return;
				}

				if (stopped) {
					msgUser(channel, user, "Stopped timer");
					return;
				}

				time--;
			}

			msgUser(channel, user, "Time's up!");
			removeThis();
		}

		public synchronized void removeThis() {
			timers.remove(this);
		}

		public String getTime() {
			final HashSet<String> timeMessages = new HashSet<>();
			long timeLeft = time;

			for (TimeUnit timeUnit : TimeUnit.values()) {
				int units = (int) (timeLeft / timeUnit.seconds);
				timeLeft -= (units * timeUnit.seconds);

				if (units != 0) {
					timeMessages.add(units + " " + timeUnit.name().toLowerCase() + (units != 1 ? "s" : ""));
				}
			}

			final StringBuilder builder = new StringBuilder();

			int i = 0;
			for (String str : timeMessages) {
				builder.append(str);

				if (i++ != (timeMessages.size() - 1)) {
					builder.append(", ");
				}
			}

			return builder.toString();
		}

		public void cancel() {
			stopped = true;
		}

		public User getUser() {
			return user;
		}

	}

	private synchronized void msgUser(Channel channel, User user, String msg) {
		channel.sendMessage("(" + user.getNick() + ") " + msg);
	}

}
