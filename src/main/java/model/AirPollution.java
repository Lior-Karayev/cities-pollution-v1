package model;

public class AirPollution extends Pollution {
    public AirPollution(String country, String city, String region, double value) {
        super(country, city, region, value);
    }

    @Override
    public String toString() {
        return "[AIR] - " + super.toString();
    }
}
