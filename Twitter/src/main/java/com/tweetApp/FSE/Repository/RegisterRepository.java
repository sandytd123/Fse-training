package com.tweetApp.FSE.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.tweetApp.FSE.Model.Register;

public interface RegisterRepository extends MongoRepository<Register,Integer> {
	
	Optional<Register> findByemail(String email);
	
	List<Register> findAll();

}
