package com.awsdevaura.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.testng.annotations.Test;

import com.awsdevaura.models.RedshiftDataProviders;
import com.awsdevaura.utils.RedshiftQueryExecutor;

/**
 * @Experience [MerchantServices]
 * @JuanTous
 */
public class RedshiftTest {

	private final RedshiftQueryExecutor queryExecutor = new RedshiftQueryExecutor();

	@Test(dataProvider = "bankQueryProvider", dataProviderClass = RedshiftDataProviders.class, priority = 1)
	public void testBankQueryExecution(String sqlQuery) {
		queryExecutor.executeSQLQuery(sqlQuery);
	}

	@Test(dataProvider = "graphqlQueryProvider", dataProviderClass = RedshiftDataProviders.class, priority = 2)
	public void testGraphQLQueryExecution(String graphqlQuery) {
		String graphqlEndpoint = "http://localhost:3000/graphql";
		executeGraphQLQuery(graphqlQuery, graphqlEndpoint);
	}

	@Test(dataProvider = "masterCardQueryProvider", dataProviderClass = RedshiftDataProviders.class, priority = 3)
	public void testMasterCardQueryExecution(String masterCardQuery) {
		queryExecutor.executeSQLQuery(masterCardQuery);
	}

	@Test(priority = 4)
	public void testTransactionExecution() {
		List<String> queries = new ArrayList<>();
		queries.add("INSERT INTO bank_accounts (account_id, status, user_id) VALUES (1, 'active', 1)");
		queries.add("INSERT INTO bank_accounts (account_id, status, user_id) VALUES (2, 'blocked', 2)");
		// Add more queries as needed

		queryExecutor.executeTransaction(queries);
	}

	private void executeGraphQLQuery(String graphqlQuery, String graphqlEndpoint) {
		try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
			HttpPost post = new HttpPost(graphqlEndpoint);
			post.setHeader("Content-Type", "application/json");
			String jsonPayload = String.format("{\"query\": \"%s\"}", graphqlQuery);
			post.setEntity(new StringEntity(jsonPayload));

			try (CloseableHttpResponse response = httpClient.execute(post)) {
				if (response.getStatusLine().getStatusCode() == 200) {
					String responseBody = EntityUtils.toString(response.getEntity());
					System.out.println("GraphQL Response: " + responseBody);
				} else {
					System.err.println("Error executing GraphQL query: " + response.getStatusLine());
				}
			}
		} catch (IOException e) {
			System.err.println("IOException occurred: " + e.getMessage());
			e.printStackTrace();
		}
	}
}