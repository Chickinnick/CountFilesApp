package com.company;

import java.io.File;
import java.io.IOException;
import java.util.Set;

public class Main implements Runnable{



    public static void main(String[] args) {


//        new Thread(new Main()).start();

        String sourceFile = args[0];
        String outputFile = args[1];


        Set<File> setOfDirs = FileCounter.readFromFile(new File(sourceFile));
        FileCounter.countAllElements(setOfDirs);
        FileCounter.writeToFile(outputFile);

    }

    @Override
    public void run() {
        while (true) {
            try {
                if (System.in.read() == 'q') {
                    System.out.println("break");
                    break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
