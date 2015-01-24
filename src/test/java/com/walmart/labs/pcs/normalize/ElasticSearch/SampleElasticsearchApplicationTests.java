package com.walmart.labs.pcs.normalize.ElasticSearch;

import com.walmart.labs.pcs.normalize.ElasticSearch.SpringBoot.application.SampleElasticsearchApplication;
import java.net.ConnectException;

import org.junit.Rule;
import org.junit.Test;
import org.springframework.boot.test.OutputCapture;
import org.springframework.core.NestedCheckedException;

import static org.junit.Assert.assertTrue;

/**
 * Created by pzhong1 on 1/23/15.
 */
public class SampleElasticsearchApplicationTests {

    @Rule
    public OutputCapture outputCapture = new OutputCapture();

    //@Test
    public void testDefaultSettings() throws Exception {
        try {
            SampleElasticsearchApplication.main(new String[0]);
        }
        catch (IllegalStateException ex) {
            if (serverNotRunning(ex)) {
                return;
            }
        }
        String output = this.outputCapture.toString();
        assertTrue("Wrong output: " + output,
                output.contains("firstName='Alice', lastName='Smith'"));
    }

    private boolean serverNotRunning(IllegalStateException ex) {
        @SuppressWarnings("serial")
        NestedCheckedException nested = new NestedCheckedException("failed", ex) {
        };
        if (nested.contains(ConnectException.class)) {
            Throwable root = nested.getRootCause();
            if (root.getMessage().contains("Connection refused")) {
                return true;
            }
        }
        return false;
    }

}
