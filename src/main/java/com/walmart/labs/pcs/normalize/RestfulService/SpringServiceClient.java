package com.walmart.labs.pcs.normalize.RestfulService;

import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by pzhong1 on 12/15/14.
 */
public class SpringServiceClient {
    private static RestTemplate restTemplate = new RestTemplate();

    public RestTemplate getRestTemplate() {
        return restTemplate;
    }

    //spring rest client template approach
    public static String getServiceResponse(String url){
        String response = "";
        try {
            MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();
            // Note the body object as first parameter!
            HttpHeaders requestHeaders = new HttpHeaders();
            requestHeaders.set("Username", "ab");
            requestHeaders.set("EMail", "ab@gmail.com");
            HttpEntity<?> httpEntity = new HttpEntity<Object>(body, requestHeaders);
            ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);
            if(responseEntity.getStatusCode() == HttpStatus.OK){
                response = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class).getBody();
            }
        } catch (RestClientException e) {
            e.printStackTrace();
        }
        return response;
    }

    //spring rest client template approach to handle bracket in url
    public static String getServiceResponse(String url, String where, String maxResult, int page){
        String response = "";
        try {
            MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();

            String urlTemplate = url + "?where={where}&max_results={max_results}&page={page}";
            // Note the body object as first parameter!
            HttpHeaders requestHeaders = new HttpHeaders();
            requestHeaders.set("Username", "ab");
            requestHeaders.set("EMail", "ab@gmail.com");
            HttpEntity<?> httpEntity = new HttpEntity<Object>(body, requestHeaders);
            Map<String, String> uriParams = new HashMap<String, String>();
            uriParams.put("where", where);
            uriParams.put("max_results", maxResult);
            uriParams.put("page", String.valueOf(page));
            ResponseEntity<String> responseEntity = restTemplate.exchange(urlTemplate, HttpMethod.GET, httpEntity, String.class, uriParams);
            if(responseEntity.getStatusCode() == HttpStatus.OK){
                response = responseEntity.getBody();
            }
        } catch (RestClientException e) {
            e.printStackTrace();
        }
        return response;
    }

    public static void main(String[] args) {
        SpringServiceClient client = new SpringServiceClient();
        RestTemplate restTemplate = client.getRestTemplate();
        String greeting = restTemplate.getForObject("http://localhost:9000/greeting?name=test", String.class);
        System.out.println(greeting);
    }
}
