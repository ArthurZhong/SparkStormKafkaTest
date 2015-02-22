package com.walmart.labs.pcs.normalize.aop.monitor;

import com.google.common.base.Stopwatch;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

/**
 * Created by pzhong1 on 2/12/15.
 */
@Aspect
@Component
public class AopExample {
    private static final Logger logger = LogManager.getFormatterLogger("Foo");

    @Pointcut("execution(* com.walmart.labs.pcs.normalize.aop.service.*.getName())")
    private void getNamePointcut(){}

    @Before("getNamePointcut()")
    public void getPeopleNamePointcutBefore(){
        System.out.println("in aop before");
    }

    @After("getNamePointcut()")
    public void getPeopleNamePointcutAfter(){
        System.out.println("in aop after");
    }

    @Pointcut("execution(* com.walmart.labs.pcs.normalize.aop.service.*.setName(..)) && args(name)")
    private void setNamePointcut(String name){}

    @Before("setNamePointcut(name)")
    public void setPeopleNamePointcutBefore(String name){
        System.out.println("in aop before and name is: " + name);
    }

    @After("setNamePointcut(name)")
    public void setPeopleNamePointcutAfter(String name){
        System.out.println("in aop after and name is: " + name);
    }

    @Around("setNamePointcut(name)")
    public Object profile(ProceedingJoinPoint call, String name) throws Throwable {
        Stopwatch clock = Stopwatch.createStarted();
        try {
            return call.proceed();
        } finally {
            clock.stop();
            long millis = clock.elapsed(MILLISECONDS);
            String message = String.format("[apiResponseTime=>%s] ms", millis);
            logger.info(message);
        }
    }

    @Around("getNamePointcut()")
    public Object profile(ProceedingJoinPoint call) throws Throwable {
        Stopwatch clock = Stopwatch.createStarted();
        try {
            return call.proceed();
        } finally {
            clock.stop();
            long millis = clock.elapsed(MILLISECONDS);
            String message = String.format("[apiResponseTime=>%s] ms", millis);
            logger.info(message);
        }
    }

}
