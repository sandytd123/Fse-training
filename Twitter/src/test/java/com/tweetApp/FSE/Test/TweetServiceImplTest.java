package com.tweetApp.FSE.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;

import com.tweetApp.FSE.DTO.RegisterDTO;
import com.tweetApp.FSE.DTO.ReplyDTO;
import com.tweetApp.FSE.DTO.TweetRequestDTO;
import com.tweetApp.FSE.DTO.TweetResponseDTO;
import com.tweetApp.FSE.Model.Register;
import com.tweetApp.FSE.Model.Reply;
import com.tweetApp.FSE.Model.Tweet;
import com.tweetApp.FSE.Repository.RegisterRepository;
import com.tweetApp.FSE.Repository.ReplyRepository;
import com.tweetApp.FSE.Repository.TweetRepository;
import com.tweetApp.FSE.Repository.UserRepository;
import com.tweetApp.FSE.ServiceImpl.TweetServiceImpl;
import com.tweetApp.FSE.ServiceImpl.UserServiceImpl;

public class TweetServiceImplTest {
	
	private MockMvc mockMvc;
	
	@Mock
	private UserRepository userrepo;
	
	@Mock
	private RegisterRepository registerrepo;
	
	@Mock
	private TweetRepository tweetRepo;
	
	@Mock
	private ReplyRepository replyRepo;
	
	@InjectMocks
	private TweetServiceImpl twitterServiceMock=new TweetServiceImpl();
	
	@BeforeEach
	public void setup() {

		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void postTweetPositiveTest() throws Exception {
		
		TweetRequestDTO tweetRequest=new TweetRequestDTO();
		tweetRequest.setEmailId("fse@gmail.com");
		tweetRequest.setTweetDesc("Desc");
		tweetRequest.setTweetTag("tag");
		
		Register register = new Register();
		register.setEmail("fse@gmail.com");
		
		String expected="Success";
		
		when(tweetRepo.findByRecordActive('Y')).thenReturn(null);
		
		
		when(userrepo.findByemail("fse@gmail.com")).thenReturn(register);
		
		String actualresp=twitterServiceMock.postTweet(tweetRequest);
		
		assertEquals(expected,actualresp);
		
	
	}
	
	@Test
	public void postTweetElseTest() throws Exception {
		
		TweetRequestDTO tweetRequest=new TweetRequestDTO();
		tweetRequest.setEmailId("fse@gmail.com");
		tweetRequest.setTweetDesc("Desc");
		tweetRequest.setTweetTag("tag");
		
		Register register = new Register();
		register.setEmail("fse@gmail.com");
		
		List<Tweet> tweetList = new ArrayList<>();
		
		Tweet tweet = new Tweet();
		tweet.setEmail("fse@gmail.com");
		tweet.setId(1);
		tweet.setTweetDescription("Desc");
		tweetList.add(tweet);
		
		
		String expected="Success";
		
		when(tweetRepo.findByRecordActive('Y')).thenReturn(tweetList);
		
		
		when(userrepo.findByemail("fse@gmail.com")).thenReturn(register);
		
		String actualresp=twitterServiceMock.postTweet(tweetRequest);
		
		assertEquals(expected,actualresp);
		
	
	}
	
	@Test
	public void getAllTweetsPostiveTest() throws Exception{
		
		List<Tweet> tweetList = new ArrayList<>();
		
		Tweet tweet = new Tweet();
		tweet.setEmail("fse@gmail.com");
		tweet.setId(1);
		tweet.setTweetDescription("Desc");
		tweet.setDate("");
		tweetList.add(tweet);
		
		List<Reply> replyList=new ArrayList<>();
		
		Reply reply = new Reply();
		reply.setEmail("fse@gmail.com");
		reply.setReplyDesc("replyDesc");
		reply.setTweetId(1);
		reply.setDate("");
		
		replyList.add(reply);
		
		List<TweetResponseDTO> expectedList= new ArrayList<>();
		TweetResponseDTO expected =new TweetResponseDTO();
		expected.setDate("");
		expected.setTweetBy("fse@gmail.com");
		expected.setTweetDesc("Desc");
		expected.setTweetId(1);
		
		List<ReplyDTO> replyDTOList = new ArrayList<>();
		ReplyDTO replyDTO=new ReplyDTO();
		replyDTO.setEmail("fse@gmail.com");
		replyDTO.setReplyDesc("replyDesc");
		replyDTO.setTweetId(1);
		replyDTO.setDate("");
		replyDTOList.add(replyDTO);
		
		expected.setReplyDTOList(replyDTOList);
		
		expectedList.add(expected);
		
		
		
		when(tweetRepo.findByRecordActive('Y')).thenReturn(tweetList);
		
		when(replyRepo.findByTweetId(1)).thenReturn(replyList);
		
		List<TweetResponseDTO> actualresp=twitterServiceMock.getAllTweets();
		
		assertEquals(expectedList,actualresp);
	}
	
	@Test
	public void postReplyPositiveTest() throws Exception{
		
		ReplyDTO replyDTO = new ReplyDTO();
		replyDTO.setEmail("fse@gmail.com");
		replyDTO.setReplyDesc("replyDesc");
		replyDTO.setTweetId(1);
		
		String actualresp=twitterServiceMock.postReply(replyDTO);
		
		assertEquals("Success",actualresp);
		
	}

	@Test
	public void deleteTweetPositiveTest() throws Exception{
		
		when(tweetRepo.deleteById(1)).thenReturn(null);
		String actualresp=twitterServiceMock.deleteTweet(1);
		
		assertEquals("success",actualresp);
		
	}
}
