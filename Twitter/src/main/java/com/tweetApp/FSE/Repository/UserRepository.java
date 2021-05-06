package com.tweetApp.FSE.Repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.tweetApp.FSE.Model.Register;

public interface UserRepository extends MongoRepository<Register,Integer> {
	
	Register findByemail(String email);
	
	Register findById(int id);
	

}
