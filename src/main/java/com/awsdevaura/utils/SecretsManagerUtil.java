package com.awsdevaura.utils;

import com.amazonaws.services.secretsmanager.AWSSecretsManager;
import com.amazonaws.services.secretsmanager.AWSSecretsManagerClientBuilder;
import com.amazonaws.services.secretsmanager.model.GetSecretValueRequest;
import com.amazonaws.services.secretsmanager.model.GetSecretValueResult;

public class SecretsManagerUtil {

	public static String getSecret(String secretName) {
		AWSSecretsManager client = AWSSecretsManagerClientBuilder.standard().build();
		GetSecretValueRequest getSecretValueRequest = new GetSecretValueRequest().withSecretId(secretName);
		GetSecretValueResult getSecretValueResult = client.getSecretValue(getSecretValueRequest);
		return getSecretValueResult.getSecretString();
	}
}