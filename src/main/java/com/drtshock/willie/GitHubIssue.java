package com.drtshock.willie;

public class GitHubIssue {
	
	private String url;
	private String html_url;
	private int id;
	private int number;
	private String title;
	private String state;
	private String created_at;
	private String updated_at;
	private String closed_at;
	private String body;
	
	public String getUrl(){
		return this.url;
	}
	
	public String getHtmlUrl(){
		return this.html_url;
	}
	
	public int getId(){
		return this.id;
	}
	
	public int getNumber(){
		return this.number;
	}
	
	public String getTitle(){
		return this.title;
	}
	
	public String getState(){
		return this.state;
	}
	
	public String getCreatedTime(){
		return this.created_at;
	}
	
	public String getUpdatedTime(){
		return this.updated_at;
	}
	
	public String getClosedTime(){
		return this.closed_at;
	}
	
	public String getBody(){
		return this.body;
	}
	
}
