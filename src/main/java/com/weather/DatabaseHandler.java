package com.weather;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class DatabaseHandler {

    private Connection connection;

    public DatabaseHandler() {
        connectToDatabase();
    }

    // Establish connection to MySQL database
    private void connectToDatabase() {
        try {
            Properties props = new Properties();
            InputStream input = getClass().getClassLoader().getResourceAsStream("application.properties");

            if (input == null) {
                throw new RuntimeException("Unable to find application.properties");
            }

            props.load(input);

            String url = props.getProperty("db.url");
            String username = props.getProperty("db.username");
            String password = props.getProperty("db.password");

            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Connected to the database successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Insert weather data using separate fields
    public void insertWeatherData(String city, double temperature, String weatherCondition, long timestamp) {
        String query = "INSERT INTO weather_data (city, temperature, weather_condition, timestamp) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, city);
            pstmt.setDouble(2, temperature);
            pstmt.setString(3, weatherCondition);
            pstmt.setLong(4, timestamp);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Weather data inserted successfully.");
            }
        } catch (SQLException e) {
            System.err.println("Error inserting weather data: " + e.getMessage());
        }
    }

    // Insert weather data using WeatherData object
    public void insertWeatherData(WeatherData weatherData) {
        insertWeatherData(
                weatherData.getCity(),
                weatherData.getTempCelsius(),
                weatherData.getCondition(),
                weatherData.getTimestamp()
        );
    }

    // Fetch daily weather summary from the database
    public void getDailySummary() {
        String query = "SELECT city, AVG(temperature) AS avg_temp, MIN(temperature) AS min_temp, MAX(temperature) AS max_temp " +
                "FROM weather_data WHERE DATE(FROM_UNIXTIME(timestamp / 1000)) = CURDATE() GROUP BY city";

        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                String city = rs.getString("city");
                double avgTemp = rs.getDouble("avg_temp");
                double minTemp = rs.getDouble("min_temp");
                double maxTemp = rs.getDouble("max_temp");

                System.out.println("City: " + city + ", Avg Temp: " + avgTemp + ", Min Temp: " + minTemp + ", Max Temp: " + maxTemp);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching daily summary: " + e.getMessage());
        }
    }

    // Fetch all weather data from the database
public List<WeatherData> getWeatherData() {
    String query = "SELECT city, temperature, weather_condition, timestamp FROM weather_data";
    List<WeatherData> weatherDataList = new ArrayList<>();

    try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
        while (rs.next()) {
            String city = rs.getString("city");
            double temperature = rs.getDouble("temperature");
            String weatherCondition = rs.getString("weather_condition");
            long timestamp = rs.getLong("timestamp");

            WeatherData weatherData = new WeatherData(city, temperature, weatherCondition, timestamp);
            weatherDataList.add(weatherData);
        }
    } catch (SQLException e) {
        System.err.println("Error fetching weather data: " + e.getMessage());
    }

    return weatherDataList;
}

    // Close the database connection
    public void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
                System.out.println("Database connection closed.");
            }
        } catch (SQLException e) {
            System.err.println("Error closing database connection: " + e.getMessage());
        }
    }
}
