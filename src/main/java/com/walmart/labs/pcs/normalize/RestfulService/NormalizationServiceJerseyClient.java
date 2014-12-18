package com.walmart.labs.pcs.normalize.RestfulService;

import com.sun.jersey.api.client.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

/**
 * Created by pzhong1 on 12/10/14.
 */
public class NormalizationServiceJerseyClient implements Serializable{
    public static final Logger logger = LoggerFactory.getLogger(NormalizationServiceJerseyClient.class);
    private Client client = Client.create();
    private static final String BASE_URI = "real_service_url";
    public String getRestfuleService(String url){
        String outPut = "";
        try {
            WebResource webResource = this.client.resource(BASE_URI + url);
            ClientResponse response = webResource.accept("application/json").get(ClientResponse.class);
            if (response.getStatus() != 200) {
                logger.error("Failed to get response from url {}{}", BASE_URI, url);
                return outPut;
            }
            outPut = response.getEntity(String.class);
        } catch (UniformInterfaceException e) {
            e.printStackTrace();
        } catch (ClientHandlerException e) {
            e.printStackTrace();
        }
        return outPut;
    }

    public String postRestfuleService(String url, String pcf){
        String outPut = "";
        try {
            WebResource webResource = this.client.resource(BASE_URI + url);
            ClientResponse response = webResource.type("application/json").post(ClientResponse.class, pcf);
            if (response.getStatus() != 200) {
                logger.error("Failed to get post response from url {}{}", BASE_URI, url);
                return outPut;
            }
            outPut = response.getEntity(String.class);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return outPut;
    }

    public static void main(String[] args) {
        try {
            NormalizationServiceJerseyClient client1 = new NormalizationServiceJerseyClient();
            String output = client1.getRestfuleService("real_get_url_address");
            System.out.println("Output from Server .... \n");
            System.out.println(output);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
