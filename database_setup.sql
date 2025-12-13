CREATE DATABASE IF NOT EXISTS cities_pollution_db;
USE cities_pollution_db;

CREATE TABLE IF NOT EXISTS cities_pollution (
    City VARCHAR(255),
    `"Region"` VARCHAR(255),
    `"Country"` VARCHAR(255),
    `"AirQuality"` DOUBLE,
    `"WaterPollution"` DOUBLE
);