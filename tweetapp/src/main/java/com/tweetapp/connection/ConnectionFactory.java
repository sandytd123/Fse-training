package com.tweetapp.connection;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;

public class ConnectionFactory {
	
	public static final String URL = "jdbc.url";
    public static final String USER = "jdbc.username";
    public static final String PASS = "jdbc.password";
    public static final String Driver="jdbc.driver";
    private static Connection connection=null;
    private static Properties properties=null;
    Scanner sc=new Scanner(System.in);
   
    public static Connection getConnection() throws FileNotFoundException, IOException, ClassNotFoundException 
    {
      try {
   	  properties=new Properties();
    	  properties.load(new FileInputStream("C:\\Users\\User\\Documents\\workspace-sts-3.9.9.RELEASE\\tweetapp\\src\\main\\java\\com\\tweetapp\\connection\\db.properties"));
    	  Class.forName(properties.getProperty(Driver));
          connection=DriverManager.getConnection(properties.getProperty(URL), properties.getProperty(USER), properties.getProperty(PASS));
         
      } catch (SQLException ex) {
    	  
          throw new RuntimeException("Error connecting to the database", ex);
      }
      return connection;
    }
	// static Connection crunchifyConn = null;
//    {
//	try {
//		Class.forName("com.mysql.cj.jdbc.Driver");
//		System.out.println("Congrats - Seems your MySQL JDBC Driver Registered!");
//	} catch (ClassNotFoundException e) {
//		System.out.println("Sorry, couldn't found JDBC driver. Make sure you have added JDBC Maven Dependency Correctly");
//		e.printStackTrace();
//		return;
//	}
//	try {
//		// DriverManager: The basic service for managing a set of JDBC drivers.
//		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/tweetapp", "root", "root");
//		if (conn != null) {
//			System.out.println("Connection Successful! Enjoy. Now it's time to push data");
//		} else {
//			System.out.println("Failed to make connection!");
//		}
//	} catch (SQLException e) {
//		System.out.println("MySQL Connection Failed!");
//		e.printStackTrace();
//		return;
//	}

   

}


		
