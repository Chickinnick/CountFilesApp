package com.company;


import java.io.*;
import java.util.Formatter;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileCounter {

    public static final Logger logger = java.util.logging.Logger.getLogger("CounterLogger");

    static Vector<ResultOfCounting> resultsVector = new Vector<>();
    static int sequenceNomber = 0;
    String sourceFile;
    String outputFile;

    public FileCounter(String sourceFile, String outputFile) {

        this.sourceFile = sourceFile;
        this.outputFile = outputFile;
    }


    public static Set<File> readFromFile(File file) {

        Set<File> directories = new LinkedHashSet<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                directories.add(new File(line));
            }
        } catch (IOException e) {
            e.printStackTrace();
            logger.log(Level.WARNING, "Error while reading file");
        }
        return directories;
    }

    public static synchronized void writeToFile(String outputFile) {
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(new File(outputFile));
            for (ResultOfCounting item : resultsVector) {
                fileWriter.write(item.toString() + "\n");
                displayResults(item);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void countAllElements(Set<File> set) {

        for (File item : set) {
            new Thread(new CountThread(item, new ResultOfCounting(item.getAbsolutePath()))).start();
            logger.log(Level.INFO, "thread created");

        }

        logger.log(Level.INFO, "end of count all");

    }

    public static void displayResults(ResultOfCounting resultOfCounting) {

        Formatter formatter = new Formatter();
        System.out.println(formatter.format("|%5d|%5d|%40s|", ++sequenceNomber, resultOfCounting.getNumberOfFiles(), resultOfCounting.getDirectory()));

    }


    static class CountThread implements Runnable {

        File item;
        ResultOfCounting resultOfCounting;

        public CountThread(File item, ResultOfCounting resultOfCounting) {
            this.item = item;
            this.resultOfCounting = resultOfCounting;
            logger.log(Level.INFO, "new thread createrd {constructor}");
        }


        public ResultOfCounting countItems(File item, ResultOfCounting resultOfCounting) {


            for (File f : item.listFiles()) {
                resultOfCounting.incrementNumberOfFiles();
                if (f.isDirectory())
                    countItems(f, resultOfCounting);
        }

            return resultOfCounting;
    }


    @Override
    public void run() {
        resultsVector.add(countItems(item, resultOfCounting));
        logger.log(Level.INFO, "1 thre");
    }
}
}
