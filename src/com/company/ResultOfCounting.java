package com.company;

public class ResultOfCounting {

    private int numberOfFiles;
    private String directory;

    public ResultOfCounting(String absolutePath) {
        this.directory = absolutePath;
    }


    public void incrementNumberOfFiles() {
        numberOfFiles++;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }

    public int getNumberOfFiles() {
        return numberOfFiles;
    }

    public String getDirectory() {
        return directory;
    }

    @Override
    public String toString() {
        return directory + ";" + numberOfFiles;
    }
}
