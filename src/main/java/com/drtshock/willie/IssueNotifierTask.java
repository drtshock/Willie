package com.drtshock.willie;

import org.pircbotx.Channel;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.TimerTask;

public class IssueNotifierTask extends TimerTask {

    private Willie bot;
    private HashMap<String, Integer> lastIssues;

    public IssueNotifierTask(Willie bot) {
        this.bot = bot;
        this.lastIssues = new HashMap<String, Integer>();

        try {
            for (JenkinsJobEntry job : this.bot.jenkins.getJobs()) {
                GitHubIssue[] issues = this.bot.jenkins.getJob(job.getName()).getIssues();

                if (issues.length > 0) {
                    this.lastIssues.put(job.getName(), this.getLastIssue(issues).getNumber());
                }
            }
        } catch (FileNotFoundException e) {
            // ignore repos with issues disabled
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private GitHubIssue getLastIssue(GitHubIssue[] issues) {
        GitHubIssue lastIssue = null;
        int minNumber = 0;

        for (GitHubIssue issue : issues) {
            if (issue.getNumber() > minNumber) {
                lastIssue = issue;
                minNumber = issue.getNumber();
            }
        }

        return lastIssue;
    }

    @Override
    public void run() {
        try {
            for (JenkinsJobEntry job : this.bot.jenkins.getJobs()) {
                GitHubIssue[] issues = this.bot.jenkins.getJob(job.getName()).getIssues();

                if (issues.length > 0) {
                    int lastIssueNumber = (this.lastIssues.containsKey(job.getName())) ? this.lastIssues.get(job.getName()) : 0;
                    GitHubIssue lastIssue = this.getLastIssue(issues);

                    if (lastIssue.getNumber() > lastIssueNumber) {
                        for (Channel channel : this.bot.getChannels()) {
                            this.bot.sendMessage(channel, "New issue for " + job.getName() + " #" + lastIssue.getNumber() + " " + lastIssue.getTitle() + " (" + lastIssue.getHtmlUrl() + ")");
                        }

                        this.lastIssues.put(job.getName(), lastIssue.getNumber());
                    }
                }
            }
        } catch (FileNotFoundException e) {
            // ignore repos with issues disabled
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
