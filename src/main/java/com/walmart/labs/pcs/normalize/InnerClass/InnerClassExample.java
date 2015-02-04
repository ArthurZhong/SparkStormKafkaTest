package com.walmart.labs.pcs.normalize.InnerClass;

/**
 * Created by pzhong1 on 2/4/15.
 */
public class InnerClassExample {

    private static HelloWorld helloWorld;

    public static interface HelloWorld {
        public String sayHello();
    }

    public static void setHelloWorld(HelloWorld helloWorld) {
        InnerClassExample.helloWorld = helloWorld;
    }

    public static HelloWorld getHelloWorld() {
        return helloWorld;
    }
}
