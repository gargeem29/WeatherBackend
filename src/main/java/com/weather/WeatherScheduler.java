package com.weather;

public class WeatherScheduler {

    public static void main(String[] args) {
        // Initialize database handler
        DatabaseHandler dbHandler = new DatabaseHandler();

        // Create WeatherData object
        WeatherData weatherData = new WeatherData("Delhi", 30.5, "Clear", System.currentTimeMillis());

        // Insert weather data using the WeatherData object
        dbHandler.insertWeatherData(weatherData);

        // Fetch daily summaries and display
        dbHandler.getDailySummary();

        // Close the database connection
        dbHandler.closeConnection();
    }
}
