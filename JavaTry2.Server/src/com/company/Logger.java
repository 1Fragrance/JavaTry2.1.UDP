package com.company;

import java.io.*;

public class Logger {
    private static final String FILE_NAME = "log.txt";
    private static final String HEADER = "Responses:\n";

    public Logger() throws IOException {
        File inputFile = new File(FILE_NAME);
        if (!inputFile.exists() || !inputFile.isFile()) {
            FileOutputStream fos = new FileOutputStream(FILE_NAME);
            fos.write(HEADER.getBytes());
            fos.close();

            System.out.println("\n** Created input file **\n");
        }
    }

    public void logResult (String clientAddress, double answer) {
        var logMsg = clientAddress + ", response - " + answer;

        try (FileWriter f = new FileWriter(FILE_NAME, true);
             BufferedWriter b = new BufferedWriter(f);
             PrintWriter p = new PrintWriter(b);) {

            p.println(logMsg);

        } catch (IOException i) {
            i.printStackTrace();
        }
    }
}
