package app;

import data.CsvDataReader;
import logic.PollutionManager;

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) {

        /*
        TO-DO;
            MenuController:
                Implement menu navigation in show menu
         */

        PollutionManager manager = new PollutionManager();
        System.out.println("========= STARTING APP =========");
        System.out.println("--- Countries with highest air and water pollution ---");

//        StringBuilder output = new StringBuilder("[\n");
//        for(String country: manager.getMaxPollutionCountries()) {
//            output.append(country).append(", \n");
//        }
//        output.delete(output.length() - 3, output.length() - 1);
//        output.append("]");
//        System.out.println(output);
//
//        System.out.println("\n--- Countries with lowest air and water pollution ---");
//
//        output.setLength(0);
//        output.append("[\n");
//        for(String country: manager.getMinPollutionCountries()) {
//            output.append(country).append(", \n");
//        }
//        output.delete(output.length() - 3, output.length() - 1);
//        output.append("]");
//        System.out.println(output);
//
//        System.out.println("\n--- Region with highest air and water pollution ---");
//
//        output.setLength(0);
//        output.append("[\n");
//        for(String country: manager.getMaxPollutionRegions()) {
//            output.append(country).append(", \n");
//        }
//        output.delete(output.length() - 3, output.length() - 1);
//        output.append("]");
//        System.out.println(output);
//
//        System.out.println("\n--- Region with lowest air and water pollution ---");
//
//        output.setLength(0);
//        output.append("[\n");
//        for(String country: manager.getMinPollutionRegion()) {
//            output.append(country).append(", \n");
//        }
//        output.delete(output.length() - 3, output.length() - 1);
//        output.append("]");
//        System.out.println(output);
//
//        System.out.println("\n--- Cities with air pollution range 26-27 ---");
//
//        output.setLength(0);
//        output.append("[\n");
//        for(String city: manager.getCitiesByAirRange(26, 27)) {
//            output.append(city).append(", \n");
//        }
//        output.delete(output.length() - 3, output.length() - 1);
//        output.append("]");
//        System.out.println(output);

        System.out.println("\n--- CSV File: Cities with max watter pollution ---");

        StringBuilder output = new StringBuilder("[\n");
        try {
            for(String city: manager.getCSVMaxWaterPollutionCities()) {
                output.append("\t").append(city).append("\n");
            }

            output.append("]");
            System.out.println(output);
        } catch (FileNotFoundException e) {
            System.out.println("Couldn't find the file.");
        }

        System.out.println("\n--- CSV File: Cities with min watter pollution ---");

        output = new StringBuilder("[\n");
        try {
            for(String city: manager.getCSVMinWaterPollutionCities()) {
                output.append("\t").append(city).append("\n");
            }

            output.append("]");
            System.out.println(output);
        } catch (FileNotFoundException e) {
            System.out.println("Couldn't find the file.");
        }

        System.out.println("\n--- CSV File: Cities with max air pollution ---");

        output = new StringBuilder("[\n");
        try {
            for(String city: manager.getCSVMaxAirPollutionCities()) {
                output.append("\t").append(city).append("\n");
            }

            output.append("]");
            System.out.println(output);
        } catch (FileNotFoundException e) {
            System.out.println("Couldn't find the file.");
        }

        System.out.println("\n--- CSV File: Cities with max air pollution ---");

        output = new StringBuilder("[\n");
        try {
            for(String city: manager.getCSVMinAirPollutionCities()) {
                output.append("\t").append(city).append("\n");
            }

            output.append("]");
            System.out.println(output);
        } catch (FileNotFoundException e) {
            System.out.println("Couldn't find the file.");
        }

        System.out.println("========= END OF THE APP =========");
    }
}
