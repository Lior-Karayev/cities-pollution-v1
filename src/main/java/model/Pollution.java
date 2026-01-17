package model;

public abstract class Pollution {
    private final String country;
    private final String city;
    private final String region;
    private final double value;

    public Pollution(String country, String city, String region, double value) {
        this.country = country;
        this.city = city;
        this.region = region;
        this.value = value;
    }

    public String getCountry() {return country;}
    public String getCity() {return city;}
    public String getRegion() {return region;}
    public double getValue() {return value;}

    public String toString() {
        return String.format("%s, %s, (%s), %.2f", country, city, region, value);
    }
}
