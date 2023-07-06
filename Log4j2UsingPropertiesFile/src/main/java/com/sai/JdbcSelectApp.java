package com.sai;

import java.io.IOException;
import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.HTMLLayout;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.SimpleLayout;


public class JdbcSelectApp {
	private static Logger logger = Logger.getLogger(JdbcSelectApp.class);
	static {
		PropertyConfigurator.configure("src/main/resources/log4j.properties");
	}
	public static void main(String[] args) throws Exception {
		String url = "jdbc:mysql://localhost:3306/sai";
		String username = "root";
		String password = "root";
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(url, username,password);

			logger.info("Connection established: "+connection);
			
			statement = connection.createStatement();
			logger.info("Statement object is created: "+statement);
			String sqlSelectQuery = "SELECT ID,NAME,AGE,CITY FROM STUDENTS";
			resultSet = statement.executeQuery(sqlSelectQuery);
			logger.info("ResultSet object is created:: " + resultSet);
			System.out.println("Id\tName\tAge\tCity");
			while (resultSet.next()) {
				Integer id = resultSet.getInt(1);
				String name = resultSet.getString(2);
				Integer age = resultSet.getInt(3);
				String city = resultSet.getString(4);
				System.out.println(id + "\t" + name + "\t" + age + "\t" + city);
			}
		}catch(ClassNotFoundException ce) {			
			logger.error("Encountered an exception with DB");
		}
		catch (SQLException e) {
			logger.error("Encountered an exception with DB");
		}
		catch(Exception e) {
			logger.fatal("Encountered an Unknown exception");
		}finally {
			try {
				resultSet.close();
			}catch(SQLException se) {
				logger.error("Problem while closing the ResultSet object");
			}
			try {
				statement.close();
			}catch(SQLException se) {
				logger.error("Problem while closing the Statement object");
			}
			try {
				connection.close();
			}catch(SQLException se) {
				logger.error("Problem while closing the Connection object");
			}
		}
		connection.close();
		logger.info("Closing the connection...");
		logger.debug("JdbcSelectApp program is closing....");
	}
}