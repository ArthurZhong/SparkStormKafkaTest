package com.walmart.labs.pcs.normalize.utils;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class HttpClientFactory {
	
	//private CloseableHttpClient httpClient;
	private static RequestConfig requestConfig;
	private static final Logger logger = LoggerFactory.getLogger(HttpClientFactory.class);
	
	static{
	        requestConfig = RequestConfig.custom()
	                .setConnectTimeout(1000)
	                .setConnectionRequestTimeout(1000)
	                .setSocketTimeout(5000)
	                .build();
	}
	
	HttpClientFactory(){
	}
	
	public static CloseableHttpClient getClient() {
		
		CloseableHttpClient httpClient = HttpClients.custom()
								.setDefaultRequestConfig(requestConfig)
								.build();

		return httpClient;
	} 
	
}