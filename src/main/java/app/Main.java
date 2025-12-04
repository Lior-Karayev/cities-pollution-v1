package app;

import logic.PollutionManager;

public class Main {
    public static void main(String[] args) {

        /*
        TO-DO;
            CsvDataReader.java:
                Implement getMaxPollutionCities
                Implement getMinPollutionCities

            MenuController:
                Implement menu navigation in show menu
         */

        PollutionManager manager = new PollutionManager();
        System.out.println("========= STARTING APP =========");
        System.out.println("--- Countries with highest air and water pollution ---");

        StringBuilder output = new StringBuilder("[\n");
        for(String country: manager.getMaxPollutionCountries()) {
            output.append(country).append(", \n");
        }
        output.delete(output.length() - 3, output.length() - 1);
        output.append("]");
        System.out.println(output);

        System.out.println("\n--- Countries with lowest air and water pollution ---");

        output.setLength(0);
        output.append("[\n");
        for(String country: manager.getMinPollutionCountries()) {
            output.append(country).append(", \n");
        }
        output.delete(output.length() - 3, output.length() - 1);
        output.append("]");
        System.out.println(output);

        System.out.println("\n--- Region with highest air and water pollution ---");

        output.setLength(0);
        output.append("[\n");
        for(String country: manager.getMaxPollutionRegions()) {
            output.append(country).append(", \n");
        }
        output.delete(output.length() - 3, output.length() - 1);
        output.append("]");
        System.out.println(output);

        System.out.println("\n--- Region with lowest air and water pollution ---");

        output.setLength(0);
        output.append("[\n");
        for(String country: manager.getMinPollutionRegion()) {
            output.append(country).append(", \n");
        }
        output.delete(output.length() - 3, output.length() - 1);
        output.append("]");
        System.out.println(output);

        System.out.println("\n--- Cities with air pollution range 26-27 ---");

        output.setLength(0);
        output.append("[\n");
        for(String city: manager.getCitiesByAirRange(26, 27)) {
            output.append(city).append(", \n");
        }
        output.delete(output.length() - 3, output.length() - 1);
        output.append("]");
        System.out.println(output);

        System.out.println("========= END OF THE APP =========");
    }
}
