package com.awsdevaura.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class RedshiftConnectionUtils {

	static {
		try {
			Class.forName("com.amazon.redshift.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Redshift JDBC Driver not found.", e);
		}
	}

	public static Connection getConnection() throws SQLException {
		String dbUrl = ConfigReaderManager.getProperty("db.url");
		String username = ConfigReaderManager.getProperty("db.username");
		String password = ConfigReaderManager.getProperty("db.password");

		return DriverManager.getConnection(dbUrl, username, password);
	}
}