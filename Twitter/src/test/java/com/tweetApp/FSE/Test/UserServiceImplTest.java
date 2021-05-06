package com.tweetApp.FSE.Test;

import org.mockito.InjectMocks;

import com.tweetApp.FSE.ServiceImpl.UserServiceImpl;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
import com.tweetApp.FSE.DTO.LoginRequestDTO;
import com.tweetApp.FSE.DTO.LoginResponseDTO;
import com.tweetApp.FSE.DTO.RegisterDTO;
import com.tweetApp.FSE.DTO.ReplyDTO;
import com.tweetApp.FSE.DTO.ResetPasswordRequestDTO;
import com.tweetApp.FSE.DTO.ResetPasswordResponseDTO;
import com.tweetApp.FSE.DTO.UserDTO;
import com.tweetApp.FSE.DTO.UserTweetsDTO;
import com.tweetApp.FSE.Model.Register;
import com.tweetApp.FSE.Model.Reply;
import com.tweetApp.FSE.Model.Tweet;
import com.tweetApp.FSE.Repository.RegisterRepository;
import com.tweetApp.FSE.Repository.ReplyRepository;
import com.tweetApp.FSE.Repository.TweetRepository;
import com.tweetApp.FSE.Repository.UserRepository;

public class UserServiceImplTest {
	
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
	private UserServiceImpl userServiceMock=new UserServiceImpl();
	
	@BeforeEach
	public void setup() {

		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void registerPositiveTest() throws Exception {
		RegisterDTO registerDTO = new RegisterDTO();
		registerDTO.setId(1);
		registerDTO.setEmail("fse@gmail.com");
		registerDTO.setFirstName("admin");
		registerDTO.setLastName("admin");
		registerDTO.setPassword("admin");
		registerDTO.setGender("male");
		
		when(userrepo.findByemail("fse@gmail.com")).thenReturn(null);
		
		when(userrepo.findById(1)).thenReturn(null);
		RegisterDTO actualresp=userServiceMock.register(registerDTO);
		
		assertEquals(registerDTO,actualresp);
	}
	
	@Test
	public void registerNegativeTest() throws Exception {
		
		RegisterDTO expectedres = new RegisterDTO();
		expectedres.setErrorMessage("Entered E-mail/Login Id Already Exists");
		expectedres.setId(1);
		expectedres.setEmail("fse@gmail.com");
		RegisterDTO registerDTO=new RegisterDTO();
		registerDTO.setId(1);
		registerDTO.setEmail("fse@gmail.com");
		
		Register resgister=new Register();
		resgister.setEmail("fse@gmail.com");
		resgister.setId(1);
		
		when(userrepo.findByemail("fse@gmail.com")).thenReturn(resgister);
		
		when(userrepo.findById(1)).thenReturn(resgister);
		
		RegisterDTO actualresp=userServiceMock.register(registerDTO);
		
		assertEquals(expectedres,actualresp);
		
	}
	
	@Test
	public void signUpPostiveTest() throws Exception{
		LoginRequestDTO loginRequestDTO=new LoginRequestDTO();
		loginRequestDTO.setEmail("fse@gmail.com");
		loginRequestDTO.setPassword("pass");
		
		LoginResponseDTO expected=new LoginResponseDTO();
		expected.setStatus(true);
		expected.setEmail("fse@gmail.com");
		Register register=new Register();
		register.setEmail("fse@gmail.com");
		register.setPassword("pass");
		
		when(userrepo.findByemail("fse@gmail.com")).thenReturn(register);
		
		LoginResponseDTO actual=userServiceMock.signUp(loginRequestDTO);
		assertEquals(expected,actual);
	}
	
	@Test
	public void signUpElseTest() throws Exception{
		LoginRequestDTO loginRequestDTO=new LoginRequestDTO();
		loginRequestDTO.setEmail("fse@gmail.com");
		loginRequestDTO.setPassword("passs");
		
		LoginResponseDTO expected=new LoginResponseDTO();
		expected.setStatus(false);
		expected.setErrorMessage("Incorrect UserName/Password");
		Register register=new Register();
		register.setEmail("fse@gmail.com");
		register.setPassword("pass");
		
		when(userrepo.findByemail("fse@gmail.com")).thenReturn(register);
		
		LoginResponseDTO actual=userServiceMock.signUp(loginRequestDTO);
		assertEquals(expected,actual);
	}
	
	@Test
	public void signUpNullTest() throws Exception{
		LoginRequestDTO loginRequestDTO=new LoginRequestDTO();
		loginRequestDTO.setEmail("fse@gmail.com");
		loginRequestDTO.setPassword("passs");
		
		LoginResponseDTO expected=new LoginResponseDTO();
		expected.setStatus(false);
		expected.setErrorMessage("Incorrect UserName/Password");
		
		when(userrepo.findByemail("fse@gmail.com")).thenReturn(null);
		
		LoginResponseDTO actual=userServiceMock.signUp(loginRequestDTO);
		assertEquals(expected,actual);
	}
	
	@Test
	public void resetpasswordPositiveTest() throws Exception{
		
		ResetPasswordRequestDTO resetRequest=new ResetPasswordRequestDTO();
		resetRequest.setOldpassword("old");
		resetRequest.setNewpassword("new");
		resetRequest.setEmailId("fse@gmail.com");
		
		ResetPasswordResponseDTO expected = new ResetPasswordResponseDTO();
		expected.setSuccessMessage("Password Updated Successfully");
		
		Register register= new Register();
		register.setEmail("fse@gmail.com");
		register.setPassword("old");
		
		when(registerrepo.findByemail("fse@gmail.com")).thenReturn(Optional.of(register));
		
		ResetPasswordResponseDTO actual=userServiceMock.resetpassword(resetRequest);
		assertEquals(expected,actual);
	}
	
	@Test
	public void resetpasswordElseTest() throws Exception{
		
		ResetPasswordRequestDTO resetRequest=new ResetPasswordRequestDTO();
		resetRequest.setOldpassword("new");
		resetRequest.setNewpassword("new");
		resetRequest.setEmailId("fse@gmail.com");
		
		ResetPasswordResponseDTO expected = new ResetPasswordResponseDTO();
		expected.setErrorMessage("Entered Old Password is Incorrect");
		
		Register register= new Register();
		register.setEmail("fse@gmail.com");
		register.setPassword("old");
		
		when(registerrepo.findByemail("fse@gmail.com")).thenReturn(Optional.of(register));
		
		ResetPasswordResponseDTO actual=userServiceMock.resetpassword(resetRequest);
		assertEquals(expected,actual);
	}
	
	@Test
	public void resetpasswordNullTest() throws Exception{
		
		ResetPasswordRequestDTO resetRequest=new ResetPasswordRequestDTO();
		resetRequest.setOldpassword("new");
		resetRequest.setNewpassword("new");
		resetRequest.setEmailId("fse@gmail.com");
		
		ResetPasswordResponseDTO expected = new ResetPasswordResponseDTO();
		expected.setErrorMessage("Entered Old Password is Incorrect");
		
		Register register= new Register();
		register.setEmail("fse@gmail.com");
		register.setPassword("old");
		
		when(registerrepo.findByemail("fse@gmail.com")).thenReturn(Optional.empty());
		
		ResetPasswordResponseDTO actual=userServiceMock.resetpassword(resetRequest);
		assertEquals(expected,actual);
	}
	
	@Test
	public void getAllUsersPositiveTest() throws Exception{
		
		List<Register> registerList = new ArrayList<>();
		Register register = new Register();
		register.setEmail("fse@gmail.com");
		register.setId(1);
		register.setFirstName("fname");
		register.setGender("male");
		
		registerList.add(register);
		
		
		List<UserDTO> expectedList = new ArrayList<>();
		
		UserDTO userDTO = new UserDTO();
		userDTO.setEmail("fse@gmail.com");
		userDTO.setId(1);
		userDTO.setFirstName("fname");
		userDTO.setGender("male");
		
		expectedList.add(userDTO);
		
		
		when(registerrepo.findAll()).thenReturn(registerList);
		
		List<UserDTO> actual=userServiceMock.getAllUsers();
		assertEquals(expectedList,actual);
		
		
	}
	
	@Test
	public void getUsersTweetPositiveTest() throws Exception{
		
		List<UserTweetsDTO> expectedList = new ArrayList<>();
		
		UserTweetsDTO expecetd=new UserTweetsDTO();
		expecetd.setTweetId(1);
		expecetd.setTweetDesc("tweetDesc");
		List<ReplyDTO> replyDTOList = new ArrayList<>();
		ReplyDTO replyDTO=new ReplyDTO();
		replyDTO.setEmail("fse@gmail.com");
		replyDTO.setReplyDesc("replyDesc");
		replyDTO.setTweetId(1);
		replyDTOList.add(replyDTO);
		
		expecetd.setReplyDTOList(replyDTOList);
		
		expectedList.add(expecetd);
		
		
		List<Tweet> tweetList = new ArrayList<>();
		
		Tweet tweet = new Tweet();
		tweet.setEmail("fse@gmail.com");
		tweet.setId(1);
		tweet.setTweetDescription("tweetDesc");
		
		tweetList.add(tweet);
		
		when(tweetRepo.findByEmail("fse@gmail.com")).thenReturn(tweetList);
		
		List<Reply> replyList = new ArrayList<>();
		Reply reply= new Reply();
		reply.setEmail("fse@gmail.com");
		reply.setReplyDesc("replyDesc");
		reply.setTweetId(1);
		replyList.add(reply);
		
		when(replyRepo.findByTweetId(1)).thenReturn(replyList);
		
		List<UserTweetsDTO> actual=userServiceMock.getUsersTweet("fse@gmail.com");
		assertEquals(expectedList,actual);
		
		
		
	}

}
