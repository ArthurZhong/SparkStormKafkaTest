package com.walmart.labs.pcs.normalize.InnerClassExample;

import com.walmart.labs.pcs.normalize.InnerClass.HelloWorldImp;
import com.walmart.labs.pcs.normalize.InnerClass.InnerClassExample;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by pzhong1 on 2/4/15.
 */
public class InnerClassExampleTest {
    @Test
     public void testHelloWorld(){
        final InnerClassExample.HelloWorld helloWorldTest = new InnerClassExample.HelloWorld(){
            @Override
            public String sayHello() {
                return "in testing";
            }
        };

        assertTrue("anonymous class is not working!", "in testing".equals(helloWorldTest.sayHello()));
    }

    @Test
    public void testHelloWorldFeeding(){
        HelloWorldImp helloWorldImp = new HelloWorldImp();
        helloWorldImp.setHelloWorld();
        assertFalse("passing parameter is not working!", "in testing".equals(InnerClassExample.getHelloWorld().sayHello()));
    }
}
