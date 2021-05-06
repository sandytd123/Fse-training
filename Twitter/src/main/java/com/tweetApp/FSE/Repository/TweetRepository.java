package com.tweetApp.FSE.Repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;


import com.tweetApp.FSE.Model.Tweet;

public interface TweetRepository extends MongoRepository<Tweet,Integer> {
	
	public List<Tweet> findByRecordActive(char recordActive);

	public List<Tweet> findByEmail(String email);
	
	public Tweet deleteById(int id);
}
