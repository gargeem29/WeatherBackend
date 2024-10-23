package com.weather;

public class WeatherData {

    private String city;
    private double tempCelsius;
    private String condition;
    private long timestamp;

    public WeatherData(String city, double tempCelsius, String condition, long timestamp) {
        this.city = city;
        this.tempCelsius = tempCelsius;
        this.condition = condition;
        this.timestamp = timestamp;
    }

    // Getters and Setters

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public double getTempCelsius() {
        return tempCelsius;
    }

    public void setTempCelsius(double tempCelsius) {
        this.tempCelsius = tempCelsius;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    // New method
    public double getTemperature() {
        return tempCelsius;  // Add this method if needed
    }

    @Override
    public String toString() {
        return "WeatherData{" +
                "city='" + city + '\'' +
                ", tempCelsius=" + tempCelsius +
                ", condition='" + condition + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
