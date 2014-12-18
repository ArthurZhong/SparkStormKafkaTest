package com.walmart.labs.pcs.normalize.RestfulService;

import org.springframework.web.client.RestTemplate;

/**
 * Created by pzhong1 on 12/15/14.
 */
public class SpringServiceClient {
    private RestTemplate restTemplate = new RestTemplate();

    public RestTemplate getRestTemplate() {
        return restTemplate;
    }

    public static void main(String[] args) {
        SpringServiceClient client = new SpringServiceClient();
        RestTemplate restTemplate = client.getRestTemplate();
        String greeting = restTemplate.getForObject("http://localhost:9000/greeting?name=test", String.class);
        System.out.println(greeting);
    }
}
