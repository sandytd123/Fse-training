package com.tweetApp.FSE.ServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tweetApp.FSE.DTO.LoginRequestDTO;
import com.tweetApp.FSE.DTO.LoginResponseDTO;
import com.tweetApp.FSE.DTO.RegisterDTO;
import com.tweetApp.FSE.DTO.ReplyDTO;
import com.tweetApp.FSE.DTO.ResetPasswordRequestDTO;
import com.tweetApp.FSE.DTO.ResetPasswordResponseDTO;
import com.tweetApp.FSE.DTO.UserDTO;
import com.tweetApp.FSE.DTO.UserTweetsDTO;
import com.tweetApp.FSE.Service.UserService;
import com.tweetApp.FSE.Repository.RegisterRepository;
import com.tweetApp.FSE.Repository.ReplyRepository;
import com.tweetApp.FSE.Repository.TweetRepository;
import com.tweetApp.FSE.Model.Register;
import com.tweetApp.FSE.Model.Reply;
import com.tweetApp.FSE.Model.Tweet;
import com.tweetApp.FSE.Repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userrepo;

	@Autowired
	private RegisterRepository registerrepo;

	@Autowired
	private TweetRepository tweetRepo;

	@Autowired
	ReplyRepository replyRepo;

	@Override
	public RegisterDTO register(RegisterDTO registerDTO) {
		if (checkUniqueEmail(registerDTO).getErrorMessage() != null) {
			return registerDTO;
		}
		Register register = convertDTOToEntity(registerDTO);
		userrepo.save(register);
		registerDTO.setSucessMessage("Registration Successful");

		return registerDTO;
	}

	private RegisterDTO checkUniqueEmail(RegisterDTO registerDTO) {
		boolean check = false;
		if (!registerDTO.getEmail().isEmpty() && registerDTO.getEmail() != null) {
			Register register = userrepo.findByemail(registerDTO.getEmail());
			if (register != null) {
				registerDTO.setErrorMessage("Entered E-mail/Login Id Already Exists");
			}
			Register loginIDCheck = userrepo.findById(registerDTO.getId());
			if (loginIDCheck != null) {
				registerDTO.setErrorMessage("Entered Login Id Already Exists");
			}

		}
		return registerDTO;
	}

	private Register convertDTOToEntity(RegisterDTO registerDTO) {
		Register register = new Register();
		register.setId(registerDTO.getId());
		register.setFirstName(registerDTO.getFirstName());
		register.setLastName(registerDTO.getLastName());
		register.setGender(registerDTO.getGender());
		register.setEmail(registerDTO.getEmail());
		register.setPassword(registerDTO.getPassword());

		return register;
	}

	@Override
	public LoginResponseDTO signUp(LoginRequestDTO loginRequestDTO) {
		// TODO Auto-generated method stub
		LoginResponseDTO loginResponseDTO = new LoginResponseDTO();

		String userName = loginRequestDTO.getEmail();

		Register register = userrepo.findByemail(userName);
		if (register != null) {
			if (!register.getPassword().equals(loginRequestDTO.getPassword())) {
				loginResponseDTO.setStatus(false);
				loginResponseDTO.setErrorMessage("Incorrect UserName/Password");

				return loginResponseDTO;

			}

			loginResponseDTO.setStatus(true);

			loginResponseDTO.setEmail(register.getEmail());

			return loginResponseDTO;
		} else {
			loginResponseDTO.setStatus(false);
			loginResponseDTO.setErrorMessage("Incorrect UserName/Password");

		}

		return loginResponseDTO;
	}

	@Override
	public ResetPasswordResponseDTO resetpassword(ResetPasswordRequestDTO resetRequest) {
		// TODO Auto-generated method stub

		Optional<Register> registerOptional = Optional.empty();

		ResetPasswordResponseDTO resetPasswordResponseDTO = new ResetPasswordResponseDTO();

		registerOptional = registerrepo.findByemail(resetRequest.getEmailId());
		Register register = (registerOptional.isPresent()) ? registerOptional.get() : new Register();
		if (register.getPassword()!=null && register.getPassword().equals(resetRequest.getOldpassword())) {
			register.setPassword(resetRequest.getNewpassword());
			Register registerReturnedObject = registerrepo.save(register);
			resetPasswordResponseDTO.setSuccessMessage("Password Updated Successfully");
		} else {
			resetPasswordResponseDTO.setErrorMessage("Entered Old Password is Incorrect");
		}

		return resetPasswordResponseDTO;
	}

	@Override
	public List<UserDTO> getAllUsers() {
		// TODO Auto-generated method stub

		List<UserDTO> userDTOList = new ArrayList<>();
		List<Register> register = registerrepo.findAll();
		register.parallelStream().forEach(user -> {
			UserDTO userDTO = new UserDTO();
			userDTO.setId(user.getId());
			userDTO.setEmail(user.getEmail());
			userDTO.setFirstName(user.getFirstName());
			userDTO.setGender(user.getGender());
			userDTOList.add(userDTO);
		});

		return userDTOList;
	}

	@Override
	public List<UserTweetsDTO> getUsersTweet(String email) {
		List<UserTweetsDTO> userTweetsDTOList = new ArrayList<>();
		List<Tweet> tweetList = tweetRepo.findByEmail(email);
		tweetList.stream().forEach(tweet -> {
			UserTweetsDTO userTweetsDTO = new UserTweetsDTO();
			userTweetsDTO.setTweetId(tweet.getId());
			userTweetsDTO.setTweetDesc(tweet.getTweetDescription());
			userTweetsDTO.setDate(tweet.getDate());
			List<Reply> replyList = replyRepo.findByTweetId(tweet.getId());
			List<ReplyDTO> replyDTOList = new ArrayList<>();
			replyList.stream().forEach(reply -> {
				ReplyDTO replyDTO = new ReplyDTO();
				replyDTO.setEmail(reply.getEmail());
				replyDTO.setReplyDesc(reply.getReplyDesc());
				replyDTO.setTweetId(reply.getTweetId());
				replyDTO.setDate(reply.getDate());
				replyDTOList.add(replyDTO);
			});
			userTweetsDTO.setReplyDTOList(replyDTOList);
			userTweetsDTOList.add(userTweetsDTO);
		});
		
		return userTweetsDTOList;
	}

}
