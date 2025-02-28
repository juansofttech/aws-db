package com.awsdevaura.models;

import org.testng.annotations.DataProvider;

public class RedshiftDataProviders {

	@DataProvider(name = "bankQueryProvider")
	public static Object[][] bankQueryProvider() {
		return new Object[][] { {
				"SELECT b.account_id, b.status, u.name FROM bank_accounts b INNER JOIN users u ON b.user_id = u.id WHERE b.status = 'blocked'" },
				{ "SELECT COUNT(*) FROM bank_accounts WHERE status = 'blocked'" },
				{ "SELECT b.account_id, b.status, u.name FROM bank_accounts b INNER JOIN users u ON b.user_id = u.id WHERE b.status = 'blocked' AND u.active = true" },
				{ "SELECT m.id AS merchant_id, m.name, COUNT(t.transaction_id) AS total_transactions, SUM(t.amount) AS total_amount FROM merchants m LEFT JOIN transactions t ON m.id = t.merchant_id GROUP BY m.id, m.name ORDER BY total_amount DESC;" },
				{ "SELECT u.id AS user_id, u.name, AVG(t.amount) AS average_transaction_amount FROM users u INNER JOIN transactions t ON u.id = t.user_id GROUP BY u.id, u.name HAVING AVG(t.amount) > 0 ORDER BY average_transaction_amount DESC;" },
				{ "SELECT u.id AS user_id, u.name, c.credit_limit FROM users u INNER JOIN credit_cards c ON u.id = c.user_id WHERE c.credit_limit > 10000 ORDER BY c.credit_limit DESC;" },
				{ "SELECT DATE_FORMAT(t.transaction_date, '%Y-%m') AS transaction_month, COUNT(t.transaction_id) AS total_transactions, SUM(t.amount) AS total_amount FROM transactions t GROUP BY transaction_month ORDER BY transaction_month DESC;" } };
	}

	@DataProvider(name = "graphqlQueryProvider")
	public static Object[][] graphqlQueryProvider() {
		return new Object[][] { { "query { transactions { id amount transactionDate merchant { id name } } }" } };
	}

	@DataProvider(name = "masterCardQueryProvider")
	public static Object[][] masterCardQueryProvider() {
		return new Object[][] { {
				"SELECT t.id, t.amount, t.transaction_date, m.name AS merchant_name FROM transactions t INNER JOIN merchants m ON t.merchant_id = m.id WHERE t.card_type = 'MasterCard'" } };
	}
}