package com.walmart.labs.pcs.normalize.Kafka;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

import java.util.Properties;

/**
 * Created by pzhong1 on 12/8/14.
 * need to change realbroker to actual kafka broker server
 */
public class KafkaProducer {
    private static final Producer<String, String> producer = getProducer();
    private static Producer<String, String> getProducer(){
        Properties props = new Properties();
        props.put("metadata.broker.list", "realbroker:9092");
        props.put("serializer.class", "kafka.serializer.StringEncoder");
        props.put("partitioner.class", "com.walmart.labs.pcs.normalize.Kafka.SimplePartitioner");
        props.put("request.required.acks", "1");
        ProducerConfig config = new ProducerConfig(props);
        Producer<String, String> producer = new Producer<String, String>(config);
        return producer;
    }

    public static void sendSingleMessage(String topic, String productId, String msg){
        KeyedMessage<String, String> data = new KeyedMessage<String, String>(topic, productId, msg);
        producer.send(data);
    }

}
