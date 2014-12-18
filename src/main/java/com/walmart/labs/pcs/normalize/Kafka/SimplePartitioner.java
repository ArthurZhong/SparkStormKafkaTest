package com.walmart.labs.pcs.normalize.Kafka;

import com.clearspring.analytics.util.Lists;
import kafka.producer.Partitioner;
import kafka.utils.VerifiableProperties;

import java.util.List;
import java.util.Random;

/**
 * Created by pzhong1 on 12/8/14.
 */
public class SimplePartitioner implements Partitioner {
    public SimplePartitioner (VerifiableProperties props) {

    }
    public int partition(Object key, int a_numPartitions) {
        List<Integer> list = Lists.newArrayList();
        for(int i = 0; i < a_numPartitions; i++){
            list.add(i);
        }
        int index = new Random().nextInt(list.size());
        return list.get(index);
    }
}
