package com.walmart.labs.pcs.normalize.Kafka;

import com.walmart.labs.pcs.normalize.RestfulService.NormalizationService;
import com.walmart.labs.pcs.normalize.utils.HttpURLConnectionUtil;
import com.walmart.labs.pcs.normalize.utils.JsonUtil;
import org.codehaus.jackson.JsonNode;

import java.util.Iterator;

/**
 * Created by pzhong1 on 12/8/14.
 */
public class PCFSampleProducer {
    public static void main(String[] args) {
        HttpURLConnectionUtil http = new HttpURLConnectionUtil();
        try {
            //restfulUrlAddress should be a valid restful url get address
            String docResponses = http.sendGet("restfulUrlAddress");
            JsonNode obj = JsonUtil.readTree(docResponses);
            JsonNode pcfDocs = obj.get("docs");
            Iterator<JsonNode> jsonNodeIterator = pcfDocs.iterator();
            NormalizationService normalizationService = new NormalizationService("http://pg-dev02.sv.walmartlabs.com:13080");
            String curDir = System.getProperty("user.dir") + "/src/main/resources/responses/";
            while (jsonNodeIterator.hasNext()) {
                JsonNode node = jsonNodeIterator.next();
                String productId = node.path("product_id").getTextValue();
                KafkaProducer.sendSingleMessage("pcfSamples", productId, JsonUtil.writeValueAsString(node));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
