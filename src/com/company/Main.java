package com.company;

import java.io.IOException;
import java.util.logging.Logger;

public class Main implements Runnable{

    public static final Logger logger = java.util.logging.Logger.getLogger("CounterLogger");


    public static void main(String[] args) {

        new Thread(new Main()).start();

        String sourceFile = args[0];
        String outputFile = args[1];

        new Thread(new FileCounter(sourceFile, outputFile)).start();

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
