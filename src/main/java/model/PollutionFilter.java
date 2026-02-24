package model;

public class PollutionFilter {
    private final String searchInTerm;
    private final boolean searchInCity;
    private final boolean searchInCountry;
    private final boolean searchInRegion;

    private final double minAirQuality;
    private final double maxAirQuality;
    private final double minWaterPollution;
    private final double maxWaterPollution;

    public PollutionFilter(Builder builder) {
        this.searchInTerm = builder.searchTerm;
        this.searchInCity = builder.searchInCity;
        this.searchInCountry = builder.searchInCountry;
        this.searchInRegion = builder.searchInRegion;
        this.minAirQuality = builder.minAirQuality;
        this.maxAirQuality = builder.maxAirQuality;
        this.minWaterPollution = builder.minWaterPollution;
        this.maxWaterPollution = builder.maxWaterPollution;
    }

    public String getSearchTerm() {return searchInTerm;}
    public boolean isSearchInCity() {return searchInCity;}
    public boolean isSearchInCountry() {return searchInCountry;}
    public boolean isSearchInRegion() {return searchInRegion;}

    public double getMinAirQuality() {
        return minAirQuality;
    }

    public double getMaxAirQuality() {
        return maxAirQuality;
    }

    public double getMinWaterPollution() {
        return minWaterPollution;
    }

    public double getMaxWaterPollution() {
        return maxWaterPollution;
    }

    // Builder pattern
    public static class Builder {
        private String searchTerm;
        private boolean searchInCity = true;
        private boolean searchInCountry = true;
        private boolean searchInRegion = true;
        private double minAirQuality = 0;
        private double maxAirQuality = 1000;
        private double minWaterPollution = 0;
        private double maxWaterPollution = 1000;

        public Builder setSearchTerm(String term) {
            this.searchTerm = term == null ? "" : term.trim();
            return this;
        }

        public Builder setSearchScope(boolean city, boolean country, boolean region) {
            this.searchInCity = city;
            this.searchInCountry = country;
            this.searchInRegion = region;
            return this;
        }

        public Builder setAirQualityRange(double min, double max) {
            this.minAirQuality = min;
            this.maxAirQuality = max;
            return this;
        }
        public Builder setWaterPollutionRange(double min, double max) {
            this.minWaterPollution = min;
            this.maxWaterPollution = max;
            return this;
        }

        public PollutionFilter build() {
            return new PollutionFilter(this);
        }
    }
}
