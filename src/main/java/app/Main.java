package app;

import data.CsvDataReader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println(CsvDataReader.getCsvFileLines("data.csv"));
    }
}
