package com.tweetApp.FSE.Service;

import java.util.List;

import com.tweetApp.FSE.DTO.ReplyDTO;
import com.tweetApp.FSE.DTO.TweetRequestDTO;
import com.tweetApp.FSE.DTO.TweetResponseDTO;

public interface TweetService {
	
	String postTweet(TweetRequestDTO tweerRequest);
	
	List<TweetResponseDTO> getAllTweets();

	String postReply(ReplyDTO replyDTO);

	String deleteTweet(int tweetId);

}
