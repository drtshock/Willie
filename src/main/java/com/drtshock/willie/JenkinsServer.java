package com.drtshock.willie;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

public class JenkinsServer {
	
	private String baseURL;
	private HashMap<String, JenkinsJob> jobs;
	
	public JenkinsServer(String baseURL){
		this.baseURL = baseURL;
		this.jobs = new HashMap<String, JenkinsJob>();
	}
	
	public JenkinsJobEntry[] getJobs() throws IOException {
		URL url = new URL(this.baseURL + "/api/json");
		
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		
		connection.setConnectTimeout(5000);
		connection.setReadTimeout(5000);
		connection.setUseCaches(false);
		
		BufferedReader input = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		
		JenkinsJobEntry[] jobs = Willie.gson.fromJson(Willie.parser.parse(input).getAsJsonObject().get("jobs"), JenkinsJobEntry[].class);
		
		input.close();
		
		return jobs;
	}
	
	public JenkinsJob getJob(String jobName) throws IOException {
		JenkinsJob job = this.jobs.get(jobName);
		
		if (job == null){
			URL url = new URL(this.baseURL + "/job/" + jobName + "/api/json");
			
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			
			connection.setConnectTimeout(5000);
			connection.setReadTimeout(5000);
			connection.setUseCaches(false);
			
			BufferedReader input = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			
			job = Willie.gson.fromJson(input, JenkinsJob.class);
			
			input.close();
			
			if (job != null){
				this.jobs.put(jobName, job);
			}
		}
		
		return job;
	}
	
}
