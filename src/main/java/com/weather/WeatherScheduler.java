package com.weather;

public class WeatherScheduler {

    public static void main(String[] args) {
        // Initialize database handler
        DatabaseHandler dbHandler = new DatabaseHandler();

        // Example of inserting weather data
        dbHandler.insertWeatherData("Delhi", 30.5, "Clear", System.currentTimeMillis());

        // Fetch daily summaries
        dbHandler.getDailySummary();

        // Close the database connection
        dbHandler.closeConnection();
    }
}
