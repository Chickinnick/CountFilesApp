package com.company;

import java.io.File;
import java.util.concurrent.Callable;

public class FilesConter/* implements Callable<ResultOfCounting>*/ {

   /* private final File directory;

    public FilesConter(File directory) {
        this.directory = directory;
    }

    @Override
    public ResultOfCounting call() throws Exception {
        ResultOfCounting resultOfCounting = countItems(directory, );
        return resultOfCounting;
    }

    private ResultOfCounting countItems(File item, ResultOfCounting resultOfCounting) {

        for (File f : item.listFiles()) {
            resultOfCounting.incrementNumberOfFiles();

            if (f.isDirectory())
                countItems(f, resultOfCounting);
        }
        return resultOfCounting;
    }*/
}
