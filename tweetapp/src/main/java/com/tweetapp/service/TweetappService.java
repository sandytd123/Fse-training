package com.tweetapp.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;
import com.tweetapp.dao.TweetDao;
import com.tweetapp.modal.Registration;
import com.tweetapp.modal.UserLogin;
import com.tweetapp.modal.UserTweet;

public class TweetappService {
	static Scanner sc = new Scanner(System.in);
	static boolean islogin = false;
	static Registration user = new Registration();
	TweetDao tweetdao = new TweetDao();
	String email;

	public String adduser() throws FileNotFoundException, ClassNotFoundException, IOException, ParseException {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		System.out.println("Enter your first name");
		String firstname = sc.nextLine();

		System.out.println("Enter your lastname");
		String lastname = sc.nextLine();

		System.out.println("Enter your gender");
		String gender = sc.nextLine();

		System.out.println("Enter your dob");
		SimpleDateFormat dateinput = new SimpleDateFormat("dd-MM-yyyy");
		java.util.Date mydate = dateinput.parse(br.readLine());
		java.sql.Date dd = new java.sql.Date(mydate.getTime());

		boolean result1;
		do {
			System.out.println("Enter your email");
			email = sc.nextLine();
			String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." + "[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-z"
					+ "A-Z]{2,7}$";
			result1 = email.matches(emailRegex);
			if (!result1) {

				System.out.println("email isnot valid");
				System.out.println("- ---- ---- ---- ---");

				System.out.println("enter a valid email");
			}
		} while (!result1);

		

		System.out.println("Enter your password");
		String password = user.getPassword();
		password = sc.nextLine();

		Registration userdetail = new Registration(firstname, lastname, gender, dd, email, password, false);
		int result = tweetdao.register(userdetail);
		if (result == 1) {
			return "successfully registered";
		} else {
			return "error exiting";
		}

	}

	public int userlogin(String email, String password) throws IOException, ParseException {
		UserLogin login = new UserLogin(email, password);
		int status = tweetdao.Login(login);
		if (status == 1) {
			int loginStatus = tweetdao.changeLoginStatus(login);
			return loginStatus;
		} else {
			return status;
		}
	}

	public int changePassword() throws IOException {

		System.out.println("Change Password");
		System.out.println("Enter Email:");
		String email = sc.nextLine();
		System.out.println("Enter New Password:");
		String password = sc.nextLine();

		UserLogin login = new UserLogin(email, password);
		int status = tweetdao.changeUserPassword(login);
		return status;
	}

	public int addTweet(String email) throws IOException {

		System.out.println("Add Tweet");

		System.out.println("Enter Tweet:");

		String tweet = sc.nextLine();
		UserTweet tweetInfo = new UserTweet(tweet, email);
		int status = tweetdao.addTweet(tweetInfo);
		return status;
	}

	public List<String> viewTweets(String email) {
		List<String> tweetList = tweetdao.viewTweets(email);
		return tweetList;
	}

	public List<Registration> alluser() throws FileNotFoundException, ClassNotFoundException, IOException {
		List<Registration> userdetail = tweetdao.getUser();
		return userdetail;
	}

	public int resetPassword(String email, String oldPassword, String newPassword) {
		int status = 0;
		status = tweetdao.resetPassword(email, oldPassword, newPassword);
		return status;
	}

	public int logOutUser(String email) {
		int status = 0;
		status = tweetdao.logoutUser(email);
		return status;
	}
}
