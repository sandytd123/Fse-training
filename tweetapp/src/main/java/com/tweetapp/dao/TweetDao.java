package com.tweetapp.dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.mysql.cj.jdbc.result.ResultSetMetaData;
import com.tweetapp.connection.ConnectionFactory;
import com.tweetapp.modal.Registration;
import com.tweetapp.modal.UserLogin;
import com.tweetapp.modal.UserTweet;

public class TweetDao {

	public int register(Registration user) throws FileNotFoundException, ClassNotFoundException, IOException {

		int result = 0;
		try {
			Connection conn = ConnectionFactory.getConnection();
			String insertQuerystatement = "INSERT into registration (firstname,lastname,gender,dob,email,password,isUserLoggedIn) VALUES(?,?,?,?,?,?,?)";
			PreparedStatement ps = conn.prepareStatement(insertQuerystatement);
			ps.setString(1, user.getFirstname());
			ps.setString(2, user.getLastname());
			ps.setString(3, user.getGender());
			ps.setDate(4, user.getDob());
			ps.setString(5, user.getEmail());
			ps.setString(6, user.getPassword());
			ps.setBoolean(7, user.getIsUserLoggedIn());
			result = ps.executeUpdate();

		}

		catch (SQLException ex) {
			ex.printStackTrace();

		}
		return result;

	}

	public int Login(UserLogin login) {

		int status = 0;
		try {
			Connection conn = ConnectionFactory.getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM registration WHERE email=? AND password=?");
			ps.setString(1, login.getEmail());
			ps.setString(2, login.getPassword());
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				status = 1;

			}

		} catch (Exception e) {
			e.printStackTrace();

		}
		return status;
	}

	public int changeLoginStatus(UserLogin login) {
		int status = 0;
		try {
			Connection conn = ConnectionFactory.getConnection();
			PreparedStatement ps = conn
					.prepareStatement("UPDATE registration SET isUserLoggedIn = true WHERE email = ? and password = ?");
			ps.setString(1, login.getEmail());
			ps.setString(2, login.getPassword());
			status = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	public int changeUserPassword(UserLogin login) {
		int status = 0;
		try {
			Connection conn = ConnectionFactory.getConnection();
			PreparedStatement ps = conn.prepareStatement("UPDATE registration SET password = ? WHERE email = ?");
			ps.setString(1, login.getPassword());
			ps.setString(2, login.getEmail());
			status = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	public int addTweet(UserTweet tweet) {
		int status = 0;
		try {
			Connection conn = ConnectionFactory.getConnection();
			PreparedStatement ps = conn.prepareStatement("INSERT INTO usertweet(tweet, email) VALUES(?,?)");
			ps.setString(1, tweet.getPostTweet());
			ps.setString(2, tweet.getEmail());
			status = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	public List<String> viewTweets(String email) {
		List<String> tweetList = null;
		String tweet = null;
		try {
			Connection conn = ConnectionFactory.getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT tweet FROM usertweet WHERE email = ?");
			ps.setString(1, email);
			ResultSet rs = ps.executeQuery();
			if (rs != null) {
				tweetList = new ArrayList<String>();
				while (rs.next()) {
					tweet = rs.getString("tweet");
					tweetList.add(tweet);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tweetList;
	}

	public static List<Registration> getUser() throws FileNotFoundException, ClassNotFoundException, IOException {

		Connection connection = ConnectionFactory.getConnection();
		List<Registration> userdetail = new ArrayList<Registration>();

		try {
			String getQuery = "SELECT * FROM registration ";
			PreparedStatement ps = connection.prepareStatement(getQuery);
			Statement stmt = connection.createStatement();
			ResultSet rs = ps.executeQuery();
			ResultSetMetaData r = (ResultSetMetaData) rs.getMetaData();

			while (rs.next()) {
				userdetail.add(new Registration(rs.getString("firstname"), rs.getString("lastname"),
						rs.getString("gender"), rs.getDate("dob"), rs.getString("email"), null, null));

			}

		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return userdetail;

	}

	public int resetPassword(String email, String oldPassword, String newPassword) {
		int status = 0;
		try {
			Connection conn = ConnectionFactory.getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT password FROM registration WHERE email = ?");
			ps.setString(1, email);
			ResultSet rs = ps.executeQuery();
			if (rs != null) {
				while (rs.next()) {
					if (rs.getString("password").equals(oldPassword)) {
						if (oldPassword.equals(newPassword)) {
							return status = -2;
						}
						ps = conn.prepareStatement("UPDATE registration SET password = ? WHERE email = ?");
						ps.setString(1, newPassword);
						ps.setString(2, email);
						return status = ps.executeUpdate();
					} else {
						return status = -1;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	public int logoutUser(String email) {
		int status = 0;
		try {
			Connection conn = ConnectionFactory.getConnection();
			PreparedStatement ps = conn
					.prepareStatement("UPDATE registration SET isUserLoggedIn = false WHERE email = ?");
			ps.setString(1, email);
			status = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

}
