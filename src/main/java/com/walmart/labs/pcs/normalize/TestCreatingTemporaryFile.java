package com.walmart.labs.pcs.normalize;

import org.apache.commons.io.IOUtils;

import java.io.*;

/**
 * Created by pzhong1 on 1/5/15.
 */
public class TestCreatingTemporaryFile {
    public static void main(String[] args) {
        try {
            File pcsFile = File.createTempFile("TestTemporaryFile", "test.json");
            pcsFile.deleteOnExit();

            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(pcsFile));
            bufferedWriter.append("{\"item_id\":\"1234\"}");
            bufferedWriter.newLine();
            bufferedWriter.append("{\"item_id\":\"5678\"}");
            bufferedWriter.newLine();
            bufferedWriter.close();
            BufferedReader in = null;
            try {
                in = new BufferedReader(new FileReader(pcsFile));
                String line;
                while ((line = in.readLine()) != null) {
                    System.out.println(line);
                }
            } catch (IOException handled) {
                handled.printStackTrace();
            } finally {
                IOUtils.closeQuietly(in);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
