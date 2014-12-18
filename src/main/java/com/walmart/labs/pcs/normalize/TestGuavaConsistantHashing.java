package com.walmart.labs.pcs.normalize;

import com.clearspring.analytics.util.Lists;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

import java.util.List;

/*
 * Created by pzhong1 on 11/27/14.
 */
public class TestGuavaConsistantHashing {
    public static void main(String[] args) {
        List<String> al = Lists.newArrayList();
        al.add("redis1");
        al.add("redis2");
        al.add("redis3");
        al.add("redis4");

        String[] userIds =
                {       "-84942321036308",
                        "-76029520310209",
                        "-68343931116147",
                        "-54921760962352"
                };
        HashFunction hf = Hashing.md5();

        ConsistentHash<String> consistentHash = new ConsistentHash<String>(hf, 100, al);
        for (String userId : userIds) {
            System.out.println(consistentHash.get(userId));
        }
    }
}
