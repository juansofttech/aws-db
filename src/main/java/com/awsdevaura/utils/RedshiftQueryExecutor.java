package com.awsdevaura.utils;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class RedshiftQueryExecutor {

	public void executeSQLQuery(String sqlQuery) {
		try (Connection connection = RedshiftConnectionUtils.getConnection();
				Statement statement = connection.createStatement()) {
			System.out.println("Executing SQL Query: " + sqlQuery);
			statement.executeQuery(sqlQuery);
		} catch (SQLException e) {
			System.err.println("SQL Exception: " + e.getMessage());
			e.printStackTrace();
		}
	}

	public void executeTransaction(List<String> queries) {
		try (Connection connection = RedshiftConnectionUtils.getConnection()) {
			connection.setAutoCommit(false);
			try (Statement statement = connection.createStatement()) {
				for (String query : queries) {
					statement.executeUpdate(query);
				}
				connection.commit();
				System.out.println("Transaction committed successfully.");
			} catch (SQLException e) {
				connection.rollback();
				System.err.println("Transaction rolled back due to: " + e.getMessage());
				e.printStackTrace();
			}
		} catch (SQLException e) {
			System.err.println("SQL Exception: " + e.getMessage());
			e.printStackTrace();
		}
	}
}