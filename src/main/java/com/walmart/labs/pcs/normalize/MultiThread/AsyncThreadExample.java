package com.walmart.labs.pcs.normalize.MultiThread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by pzhong1 on 4/23/15.
 */
public class AsyncThreadExample {
    public void testAsync() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            public void run() {
                System.out.println("Asynchronous task");
            }
        });
        executorService.shutdown();
    }
}
