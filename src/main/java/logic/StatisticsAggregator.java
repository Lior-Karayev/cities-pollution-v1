package logic;

import model.PollutionReportModel;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StatisticsAggregator {
    public static List<PollutionReportModel> getCountryAverage(List<PollutionReportModel> rawData) {
        // parallelStream() splits the data processing across multiple CPU cores.
        // This significantly speeds up the grouping operation on large datasets.
        Map<String, List<PollutionReportModel>> groupByCountry = rawData.parallelStream()
                .collect(Collectors.groupingBy(PollutionReportModel::getCountry));

        // Process each grouped country entry in parallel
        return groupByCountry.entrySet().parallelStream().map(entry -> {
            String country = entry.getKey();
            List<PollutionReportModel> citiesInCountry = entry.getValue();

            // Calculate averages safely. mapToDouble avoids boxing overhead,
            // and orElse(0.0) prevents crashes if a country has no valid data points.
            double avgAir = citiesInCountry.stream()
                    .mapToDouble(PollutionReportModel::getAirQuality)
                    .average().orElse(0.0);

            double avgWater = citiesInCountry.stream()
                    .mapToDouble(PollutionReportModel::getWaterPollution)
                    .average().orElse(0.0);

            return new PollutionReportModel(country, "Country Average", "N/A", avgAir, avgWater);
        }).toList();
    }

    public static List<PollutionReportModel> getRegionAverages(List<PollutionReportModel> rawData) {
        Map<String, List<PollutionReportModel>> groupedByRegion = rawData.parallelStream()
                .filter(p -> p.getRegion() != null && !p.getRegion().isEmpty())
                .collect(Collectors.groupingBy(p -> p.getCountry() + "|" + p.getRegion()));

        return groupedByRegion.entrySet().parallelStream().map(entry -> {
            List<PollutionReportModel> citiesInRegion = entry.getValue();

            PollutionReportModel first = citiesInRegion.getFirst();
            String country = first.getCountry();
            String region = first.getRegion();

            double avgAir = citiesInRegion.stream()
                    .mapToDouble(PollutionReportModel::getAirQuality)
                    .average().orElse(0.0);

            double avgWater = citiesInRegion.stream()
                    .mapToDouble(PollutionReportModel::getWaterPollution)
                    .average().orElse(0.0);

            return new PollutionReportModel(country, "Region Average", region, avgAir, avgWater);
        }).toList();
    }
}
