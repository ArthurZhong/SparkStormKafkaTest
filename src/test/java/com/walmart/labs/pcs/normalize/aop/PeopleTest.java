package com.walmart.labs.pcs.normalize.aop;

import com.walmart.labs.pcs.normalize.aop.monitor.AopExample;
import com.walmart.labs.pcs.normalize.aop.service.PeopleService;
import org.apache.log4j.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.boot.test.OutputCapture;

import java.io.StringWriter;
import java.io.Writer;

import static org.junit.Assert.assertTrue;

//import static org.junit.Assert.assertTrue;

/**
 * Created by pzhong1 on 2/12/15.
 */

public class PeopleTest {

//    @Rule
//    public OutputCapture outputCapture = new OutputCapture();
//
//    private String profiles;
//
//    @Before
//    public void init() {
//        this.profiles = System.getProperty("pcs.normalize.aop.name");
//    }
//
//    @After
//    public void after() {
//        if (this.profiles != null) {
//            System.setProperty("pcs.normalize.aop.name", this.profiles);
//        }
//        else {
//            System.clearProperty("pcs.normalize.aop.name");
//        }
//    }
//
//    @Test
//    public void testDefaultSettings() throws Exception {
//        SampleAopApplication.main(new String[0]);
//        String output = this.outputCapture.toString();
//        System.out.println(output);
//        assertTrue("Wrong output: " + output, output.contains("Phil"));
//    }

    @Test
    public void testSetGetName(){

        try {
            // Setup WriterAppender
            Writer w = new StringWriter();
            Layout l = new PatternLayout("%m%n");

            WriterAppender wa = new WriterAppender(l, w);
            wa.setEncoding("UTF-8");
            wa.setThreshold(Level.INFO);
            wa.activateOptions();// WriterAppender does nothing here, but I like defensive code...

            // Add it to logger
            Logger log = Logger.getLogger(AopExample.class);// ExceptionHandler is the class that contains this code : `log.warn("An error has occured:", e);'
            log.addAppender(wa);

            try {
                // Call to the method that will print text to STDOUT...
                PeopleService peopleService = new PeopleService();
                peopleService.setName("hello world");
                String batchLog = w.toString();
                System.out.println(batchLog);
                System.out.println(peopleService.getName());
       //         assertTrue("Output does not contain apiResponseTime", batchLog.contains("apiResponseTime"));
            } finally {
                // Cleanup everything...
                log.removeAppender(wa);
                wa.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
