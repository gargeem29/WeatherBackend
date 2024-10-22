package com.weather;

import java.io.InputStream;
import java.sql.Connection;     // For SQLException
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Properties;
import java.sql.SQLException;
import java.sql.ResultSet;

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

    // Example method to insert weather data into the database
    public void insertWeatherData(String city, double temperature, String weatherCondition, long timestamp) {
        String query = "INSERT INTO weather_data (city, temperature, weather_condition, timestamp) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, city);
            pstmt.setDouble(2, temperature);
            pstmt.setString(3, weatherCondition);
            pstmt.setLong(4, timestamp);
            pstmt.executeUpdate();
            System.out.println("Weather data inserted successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Example method to fetch daily summaries from the database
    public void getDailySummary() {
        String query = "SELECT city, AVG(temperature) AS avg_temp, MIN(temperature) AS min_temp, MAX(temperature) AS max_temp " +
                       "FROM weather_data WHERE DATE(timestamp) = CURDATE() GROUP BY city";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                String city = rs.getString("city");
                double avgTemp = rs.getDouble("avg_temp");
                double minTemp = rs.getDouble("min_temp");
                double maxTemp = rs.getDouble("max_temp");
                System.out.println("City: " + city + ", Avg Temp: " + avgTemp + ", Min Temp: " + minTemp + ", Max Temp: " + maxTemp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method to close the database connection
    public void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
                System.out.println("Database connection closed.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
