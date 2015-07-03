package com.walmart.labs.pcs.normalize.Guava;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Maps;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by pzhong1 on 5/12/15.
 */
public class CacheLoaderExample {
    private Map<String, People> map = Maps.newHashMap();
    private static final People fakePeople = new People();
    private LoadingCache<String, People> peopleLoadingCache;
    public void init() {
        CacheLoader<String, People> cacheLoader = new CacheLoader<String, People>(){
            @Override
            public People load(String attriName) throws Exception {
                return getPeople(attriName);
            }
        };
        peopleLoadingCache = CacheBuilder.newBuilder().maximumSize(10).expireAfterWrite(5, TimeUnit.MINUTES).build(cacheLoader);
    }

    private People getPeople(String attriName) {
       return map.containsKey(attriName) ? map.get(attriName) : fakePeople;
    }

    public static void main(String[] args) {

    }
}
