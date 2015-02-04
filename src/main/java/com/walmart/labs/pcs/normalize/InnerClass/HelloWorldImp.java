package com.walmart.labs.pcs.normalize.InnerClass;

/**
 * Created by pzhong1 on 2/4/15.
 */
public class HelloWorldImp implements InnerClassExample.HelloWorld{
    @Override
    public String sayHello() {
        return "hello world in imp1!";
    }

    public void setHelloWorld(){
        InnerClassExample.setHelloWorld(this);
    }

    public static void main(String[] args) {
        HelloWorldImp helloWorldImp = new HelloWorldImp();
        helloWorldImp.setHelloWorld();
        System.out.println(InnerClassExample.getHelloWorld().sayHello());
    }
}
