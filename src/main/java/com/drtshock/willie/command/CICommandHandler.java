package com.drtshock.willie.command;

import com.drtshock.willie.JenkinsJob;
import com.drtshock.willie.Willie;
import org.pircbotx.Channel;
import org.pircbotx.Colors;
import org.pircbotx.User;

import java.io.IOException;

public class CICommandHandler implements CommandHandler {

    @Override
    public void handle(Willie bot, Channel channel, User sender, String[] args) {
        if (args.length != 1) {
            String message = String.format(Colors.BLUE + "Get dev builds at %s If you're interesting in hosting a project there, talk to %s",
                    bot.getConfig().getJenkinsServer(), bot.getConfig().getJenkinsAdmins());
            channel.sendMessage(message);
        } else try {
            JenkinsJob job = bot.jenkins.getJob(args[0]);
            channel.sendMessage(String.format("Project %s %s -", job.getDisplayName(), job.getUrl()));

            // Health report
            for (JenkinsJob.HealthReport report : job.getHealthReports()) {
                String color;
                switch (report.score) {
                    case 100:
                        color = Colors.GREEN;
                        break;
                    case 80:
                        color = Colors.BLUE;
                        break;
                    default:
                        color = Colors.RED;
                        break;
                }
                String msg[] = report.description.split(":");
                channel.sendMessage(msg[0] + ":" + color + msg[1]);
            }

            // Repository url
            if (!job.getGitHubUrl().isEmpty()) {
                channel.sendMessage("Repository: " + job.getGitHubUrl());
            }

            // Issues
            if (job.getIssues().length != 0) {
                channel.sendMessage("Total issues: " + Colors.RED + job.getIssues().length);
            }

        } catch (IOException e) {
            channel.sendMessage(Colors.RED + "Sorry, I don't know anything about that project.");
        }
    }

}
