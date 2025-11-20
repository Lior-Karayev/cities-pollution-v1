package model;

public class PollutionReportModel {
    private final String country;
    private final String city;
    private final String region;
    private final double airQuality;
    private final double waterQuality;

    public PollutionReportModel(String country, String city, String region, double airQuality, double waterQuality) {
        this.country = country;
        this.city = city;
        this.region = region;
        this.airQuality = airQuality;
        this.waterQuality = waterQuality;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getRegion() {
        return region;
    }

    public double getAirQuality() {
        return airQuality;
    }

    public double getWaterQuality() {
        return waterQuality;
    }

    @Override
    public String toString() {
        return String.format("Country: %s | Region: %s | City: %s | Air Quality: %.2f | Water Quality: %.2f",
                country, region, city, airQuality, waterQuality);
    }
}
