package model;

public class WaterPollution extends Pollution {
    public WaterPollution(String country, String city, String region, double value) {
        super(country, city, region, value);
    }

    @Override
    public String toString() {
        return "[WATTER] - " + super.toString();
    }
}
