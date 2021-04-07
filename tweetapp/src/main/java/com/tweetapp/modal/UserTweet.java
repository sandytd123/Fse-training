package com.tweetapp.modal;

public class UserTweet {
	
	private String postTweet;
	private String email;
	
	public UserTweet() {
		
	}

	public UserTweet(String postTweet, String email) {
		super();
		this.postTweet = postTweet;
		this.email = email;
	}

	public String getPostTweet() {
		return postTweet;
	}

	public void setPostTweet(String postTweet) {
		this.postTweet = postTweet;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	
	
	

}
