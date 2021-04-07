package com.tweetapp;


import java.io.FileNotFoundException;
import java.io.IOException;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.tweetapp.modal.Registration;
import com.tweetapp.modal.UserLogin;
import com.tweetapp.service.TweetappService;

public class App {
	public static void main(String[] args)
			throws ClassNotFoundException, FileNotFoundException, IOException, ParseException {

		Boolean islogin = false;
		TweetappService tweetservice = new TweetappService();
		UserLogin loggedIn = new UserLogin();
		Scanner sc = new Scanner(System.in);
		while (true) {
			while (!islogin) {
				int choice;
				System.out.println("Choose from the given below option\n 1.Register \n 2.login \n3.Forget password\n");
				choice = Integer.parseInt(sc.nextLine());
				switch (choice) {
				case 1:
					System.out.println(tweetservice.adduser());
					break;
				case 2:
					System.out.println("Login User");
					System.out.println("Enter Email:");
					String email = sc.nextLine();
					System.out.println("Enter Password:");
					String password = sc.nextLine();

					int status = tweetservice.userlogin(email, password);
					if (status == 1) {
						islogin = true;
						loggedIn.setEmail(email);
						System.out.println(" Successfully Login");
					} else {
						System.out.println("Wrong Username or Password");
					}
					break;
				case 3:
					int passwordChangeStatus = tweetservice.changePassword();
					if (passwordChangeStatus == 1) {
						System.out.println("Password Changed Successfully");
					} else {
						System.out.println("UserName Not Found ,First register Yourself");
					}
					break;
				default:
					System.out.println("Please Enter the choice from thr given option");
				}
			}
			while (islogin) {
				int choice;
				System.out.println(
						" Select An Option From Below List\n1. Post a Tweet\n2. View My Tweet\n3. View All Tweets\n4. View All Users\n5. Reset Password\n6. Logout");
				choice = Integer.parseInt(sc.nextLine());
				switch (choice) {
				case 1:
					int tweetStatus = tweetservice.addTweet(loggedIn.getEmail());
					if (tweetStatus == 1) {
						System.out.println("Posted the Tweet  Successfully");
					} else {
						System.out.println("Cannot post Tweet Something Went Wrong");
					}
					break;
				case 2:
					List<String> tweetList = new ArrayList<String>();
					tweetList = tweetservice.viewTweets(loggedIn.getEmail());
					if (tweetList == null) {
						System.out.println("No Tweet Found ");
					} else {
						System.out.println("Your tweetlist");

						tweetList.forEach(tweet -> {
							System.out.println(tweet);
							System.out.println("------------------------------------------------");
						});
					}
					break;
				case 3:
					List<String> usertweet = new ArrayList<String>();
					System.out.println("Enter the particular user userid");
					String email = sc.nextLine();
					usertweet = tweetservice.viewTweets(email);
					if (usertweet == null) {
						System.out.println("No twet found of these userid");
					} else {
						System.out.println("here the tweet");
						usertweet.forEach(tweet -> {
							System.out.println(tweet);
						});
					}
					break;
				case 4:

					List<Registration> userdetail = new ArrayList<Registration>();
					userdetail = tweetservice.alluser();
					if (userdetail == null) {
						System.out.println("no user found");
					} else {
						System.out.println("user detail list");
						userdetail.forEach(user -> {
							System.out.println(user);
						});
					}

					break;
				case 5:
					System.out.println("Reset Password");
					String newPassword = null;
					int status = 0;
					System.out.println("Reset Password");

					System.out.println("Enter Old Password:");

					String oldPassword = sc.nextLine();
					System.out.println("Enter New Password:");

					newPassword = sc.nextLine();
					status = tweetservice.resetPassword(loggedIn.getEmail(), oldPassword, newPassword);
					if (status == 0) {
						System.out.println(" Unable to reset password try again");
					} else {
						if (status == -1) {
							System.out.println(" your old password is wrong .");
						} else if (status == -2) {
							System.out.println(" New password canot be same as old password");
						} else {
							System.out.println("Password Reset Successful.");
						}
						System.out.println("Please Login Again To Continue.");
						islogin = false;
						loggedIn = null;
					}

					break;
				case 6:
					System.out.println("Logging out!!!!");
					int loginStatus = tweetservice.logOutUser(loggedIn.getEmail());
					if (loginStatus == 1) {
						islogin = false;
						loggedIn = null;
						System.out.println("User Logged out Successfully");
					} else {
						System.out.println("Logging Out Failed Try Again");
					}
					break;

				default:
					System.out.println("Please Enter a valid choice");
				}
			}
			sc.close();
		}

	}

}
