package com.walmart.labs.pcs.normalize.MultiThread;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.WriterAppender;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * Created by pzhong1 on 4/23/15.
 */
public class CallableExample {
    public String printSystemPrintAndLogTrace() {
        String logTrace = "";
        PrintStream originalSysOut = System.out;
        ByteArrayOutputStream sysOut = new ByteArrayOutputStream();
        try {
            System.setOut(new PrintStream(sysOut));
            logTrace = getLogTrace(new Callable<Object>() {
                @Override
                public Object call() throws Exception {
                    System.out.println("Hello World!");
                    Logger.getLogger(CallableExample.class).info("test message");
                    return null;
                }
            });
        } catch (Exception logged) {
            logged.printStackTrace();
        } finally {
            System.setOut(originalSysOut);
            System.out.flush();
            logTrace += sysOut.toString();
        }

        return logTrace;
    }
    private <T> String getLogTrace(Callable<T> callable) throws Exception {
        Writer writter = new StringWriter();
        WriterAppender appender = new WriterAppender(new PatternLayout("%m%n"), writter);
        appender.setEncoding("UTF-8");
        appender.setThreshold(Level.DEBUG);
        appender.activateOptions();

        Logger rootLogger = Logger.getRootLogger();
        rootLogger.addAppender(appender);
        Level originalLevel = rootLogger.getLevel();
        rootLogger.setLevel(Level.DEBUG);

        try {
            callable.call();
            return writter.toString();
        } finally {
            rootLogger.setLevel(originalLevel);
            rootLogger.removeAppender(appender);
            appender.close();
        }
    }
}
