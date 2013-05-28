package com.drtshock.willie;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class JenkinsJob {

    private String displayName;
    private String url;
    private String gitHubUrl;
    private ArrayList<HealthReport> healthReport;

    public static class HealthReport {
        public String description;
        public int score;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public String getUrl() {
        return this.url;
    }

    public ArrayList<HealthReport> getHealthReports() {
        return healthReport;
    }

    public String getGitHubUrl() throws IOException {
        if (this.gitHubUrl == null) {
            URL url = new URL(this.url);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            connection.setUseCaches(false);

            BufferedReader input = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            StringBuilder buffer = new StringBuilder();
            String page;

            while ((page = input.readLine()) != null) {
                buffer.append(page);
                buffer.append('\n');
            }

            page = buffer.toString();

            input.close();

            int linkPos = page.indexOf("github.com");

            if (linkPos == -1) {
                return null;
            }

            this.gitHubUrl = "https://github.com/" + page.substring(linkPos + 11, page.indexOf('"', linkPos));

            if (this.gitHubUrl.charAt(this.gitHubUrl.length() - 1) != '/') {
                this.gitHubUrl += '/';
            }
        }

        return this.gitHubUrl;
    }

    public GitHubIssue[] getIssues() throws IOException {
        String gitUrl = this.getGitHubUrl();

        if (gitUrl == null) {
            return new GitHubIssue[0];
        }

        URL url = new URL("https://api.github.com/repos/" + gitUrl.substring(19) + "issues");

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);
        connection.setUseCaches(false);
        //connection.setRequestProperty("Authorization", Willie.GIT_AUTH);
        connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");

        BufferedReader input = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        GitHubIssue[] issues = Willie.gson.fromJson(input, GitHubIssue[].class);

        input.close();

        return issues;
    }

}
