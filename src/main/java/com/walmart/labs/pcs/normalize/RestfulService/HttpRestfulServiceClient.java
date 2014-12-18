package com.walmart.labs.pcs.normalize.RestfulService;

import com.walmart.labs.pcs.normalize.utils.HttpClientFactory;
import org.apache.http.ConnectionClosedException;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.ConnectionPoolTimeoutException;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.protocol.HttpContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.nio.channels.ConnectionPendingException;

public class HttpRestfulServiceClient implements Serializable{

    private static Logger logger = LoggerFactory.getLogger(HttpRestfulServiceClient.class);
    private static final String ATTR_NORMALIZATION_SERVICE  = "v1.0/attribution/normalization";

    
    private String serviceEndPoint;

    public HttpRestfulServiceClient() {
        serviceEndPoint = "real_service_endpoint";
        logger.info("PCSApiService URL from EnvironmentSetup: {}", serviceEndPoint);
    }


    public String normalize(String requestStr){
        return request(getServiceUrl(ATTR_NORMALIZATION_SERVICE), requestStr);
    }
    

    private String request(String serviceUrl, String requestStr) {

    	CloseableHttpClient httpClient = HttpClientFactory.getClient();
		HttpContext context = HttpClientContext.create();
		CloseableHttpResponse response = null;
		try {

            StringEntity entity = new StringEntity(requestStr, "UTF-8");
            entity.setContentType("application/json");
            logger.info("PCSApiService URL: {}", serviceUrl);
            logger.debug("Service request: {}", requestStr);

            HttpPost postRequest = new HttpPost(serviceUrl);
            postRequest.setEntity(entity);
            long startTime = System.currentTimeMillis();
            // Make Service Call
            
			response = httpClient.execute(postRequest, context);
            long endTime = System.currentTimeMillis();
            long responseTime = endTime - startTime;
            String serviceName = getServiceName(serviceUrl);
            if (logger.isInfoEnabled()) logger.info("{} response time: {} seconds.", new Object[]{serviceName, responseTime/1000});
                        
            // Response Handling
            if (response.getStatusLine().getStatusCode() != 200) {
                logger.error("HTTP error code: {}", response.getStatusLine().getStatusCode());
                return null;
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));

            String output;
            StringBuilder responseBuilder = new StringBuilder();
            while ((output = br.readLine()) != null) {
                responseBuilder.append(output);
            }
            String responseStr = responseBuilder.toString();
            if (logger.isDebugEnabled()) logger.debug("Service response: {}", responseStr);
            return responseStr;

        } catch (MalformedURLException e) {
            logger.error(e.toString());
        } catch (ConnectionPoolTimeoutException e) {
			logger.error(e.getMessage(), e);
		} catch (ConnectTimeoutException e) {
			logger.error(e.getMessage(), e);
		} catch (NoHttpResponseException e) {
			logger.error(e.getMessage(), e);
		} catch (ConnectionClosedException e) {
			logger.error(e.getMessage(), e);
		}  catch (ConnectionPendingException e) {
			logger.error(e.getMessage(), e);
		} catch (IOException e) {
            logger.error(e.toString());
        }  catch(Exception e) {
            e.printStackTrace();
		}
		finally {
			try {
				if(response != null)
					response.close();
				if(httpClient != null)
					httpClient.close();
				} catch (IOException e) {
					logger.error(e.toString());
				}
		}
        return null;
    }
    
    private String getServiceUrl(String serviceName) {
        return serviceEndPoint + serviceName;
    }
    
    private String getServiceName(String serviceUrl) {
        if(serviceUrl != null){
            return serviceUrl.substring(serviceUrl.lastIndexOf("/") + 1);
        }
        return null;
    }
}
