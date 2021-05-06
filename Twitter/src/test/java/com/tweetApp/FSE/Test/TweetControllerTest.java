package com.tweetApp.FSE.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tweetApp.FSE.Controller.TweetController;
import com.tweetApp.FSE.DTO.RegisterDTO;
import com.tweetApp.FSE.DTO.TweetResponseDTO;
import com.tweetApp.FSE.DTO.UserDTO;
import com.tweetApp.FSE.Service.TweetService;
import com.tweetApp.FSE.ServiceImpl.TweetServiceImpl;
@ExtendWith(MockitoExtension.class)
public class TweetControllerTest {
	
	private MockMvc mockMvc;
	
	@Mock
	private TweetService tweetServiceMock = new TweetServiceImpl();
	
	@InjectMocks
	private TweetController tweetController;
	
	@SuppressWarnings("deprecation")
	@BeforeEach
	public void setup() {
		MockitoAnnotations.initMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(tweetController).build();
		
		
	}

	@Test
	public void postTweetPositiveTest() throws Exception
	{
		String reqJson="{\"tweetDesc\":\"desc\",\"emailId\":\"fse@gmail.com\"}";
		String url = "/api/v1.0/tweets/posttweet";
		
		String responseJson="Success";
		
		when(tweetServiceMock.postTweet(Mockito.any())).thenReturn("Success");
		MockHttpServletResponse response = mockMvc
				.perform(post(url).accept(MediaType.APPLICATION_JSON).content(reqJson)
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andReturn().getResponse();

		assertEquals(responseJson, response.getContentAsString());
	}
	
	
	@Test
	public void postReplyPositiveTest() throws Exception
	{
		String reqJson="{\"replyDesc\":\"desc\",\"emailId\":\"fse@gmail.com\",\"tweetId\":\"1\"}";
		String url = "/api/v1.0/tweets/reply";
		
		String responseJson="Success";
		
		when(tweetServiceMock.postReply(Mockito.any())).thenReturn("Success");
		MockHttpServletResponse response = mockMvc
				.perform(post(url).accept(MediaType.APPLICATION_JSON).content(reqJson)
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andReturn().getResponse();

		assertEquals(responseJson, response.getContentAsString());
	}
	
	@Test
	public void getAllTweetsPositiveFlow() throws Exception{
		String url="/api/v1.0/tweets/gettweets";
		List<TweetResponseDTO> tweetResponseDTOList= new ArrayList<>();
		TweetResponseDTO tweetResponseDTO= new TweetResponseDTO();
		tweetResponseDTO.setTweetBy("fse@gmail.com");
		tweetResponseDTO.setTweetDesc("desc");
		tweetResponseDTO.setTweetId(1);
		
		tweetResponseDTOList.add(tweetResponseDTO);
		
		String expectedJson="[{\"tweetDesc\":\"desc\",\"tweetBy\":\"fse@gmail.com\",\"tweetId\":1,\"date\":null,\"replyDTOList\":null}]";
		when(tweetServiceMock.getAllTweets()).thenReturn(tweetResponseDTOList);
		MockHttpServletResponse response = mockMvc.perform(get(url).accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();
		assertEquals(expectedJson, response.getContentAsString());
	}
	
	@Test
	public void deletTweetPostiveFlow() throws Exception{
		String url="/api/v1.0/tweets/delete?tweetId=1";
		
		String expectedJson="Success";
		when(tweetServiceMock.deleteTweet(1)).thenReturn("Success");
		MockHttpServletResponse response = mockMvc.perform(delete(url).accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();
		assertEquals(expectedJson, response.getContentAsString());
		
		
		
	}
	
	
	
}
