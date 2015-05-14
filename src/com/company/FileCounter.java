package com.company;


import java.io.*;
import java.util.Formatter;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.Vector;
import java.util.logging.Level;

public class FileCounter implements Runnable {

    int sequenceNomber = 0;
    String sourceFile;
    String outputFile;

    public FileCounter(String sourceFile, String outputFile) {

        this.sourceFile = sourceFile;
        this.outputFile = outputFile;
    }


    public Set<File> readFromFile(File file) {

        Set<File> directories = new LinkedHashSet<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                directories.add(new File(line));
            }
        } catch (IOException e) {
            e.printStackTrace();
            Main.logger.log(Level.WARNING, "Error while reading file");
        }
        return directories;
    }

    public void writeToFile(Vector<ResultOfCounting> results, String outputFile) {
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(new File(outputFile));
            for (ResultOfCounting item : results) {
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

    public Vector<ResultOfCounting> countAllElements(Set<File> set) {
        Vector<ResultOfCounting> results = new Vector<>();
        for (File item : set) {
            Main.logger.log(Level.INFO, "search in" + item.getAbsolutePath() + " - START");
            results.add(countItems(item, new ResultOfCounting(item.getAbsolutePath())));
            // results.add(new Thread(new CountThread(item, new ResultOfCounting(item.getAbsolutePath()))).start());
        }

        return results;
    }
    private ResultOfCounting countItems(File item, ResultOfCounting resultOfCounting) {

        for (File f : item.listFiles()) {
            resultOfCounting.incrementNumberOfFiles();

            if (f.isDirectory())
                countItems(f, resultOfCounting);
        }
        return resultOfCounting;
    }

   /* class CountThread implements Runnable {
        private ResultOfCounting countItems(File item, ResultOfCounting resultOfCounting) {

            for (File f : item.listFiles()) {
                resultOfCounting.incrementNumberOfFiles();

                if (f.isDirectory())
                    countItems(f, resultOfCounting);
            }
            return resultOfCounting;
        }

        File item;
        ResultOfCounting resultOfCounting;

        public CountThread(File item, ResultOfCounting resultOfCounting) {
            this.item = item;
            this.resultOfCounting = resultOfCounting;
        }

        @Override
        public void run() {
            countItems(item, resultOfCounting);
        }
    }*/

    public void displayResults(ResultOfCounting resultOfCounting) {

        Formatter formatter = new Formatter();
        System.out.println(formatter.format("|%5d|%5d|%40s|", ++sequenceNomber, resultOfCounting.getNumberOfFiles(), resultOfCounting.getDirectory()));

    }


    @Override
    public void run() {
        Set<File> setOfDirs = readFromFile(new File(sourceFile));
        Vector<ResultOfCounting> results = countAllElements(setOfDirs);
        writeToFile(results, outputFile);
    }
}
