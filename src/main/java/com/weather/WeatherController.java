package com.weather;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class WeatherController {

    private final DatabaseHandler databaseHandler;

    public WeatherController(DatabaseHandler databaseHandler) {
        this.databaseHandler = databaseHandler;
    }

    @GetMapping("/weather")
    public ResponseEntity<List<WeatherData>> getWeatherData() {
        // Fetch weather data from the database
        List<WeatherData> weatherDataList = databaseHandler.getWeatherData();

        if (weatherDataList.isEmpty()) {
            return ResponseEntity.noContent().build(); // Return 204 No Content if the list is empty
        }

        return ResponseEntity.ok(weatherDataList); // Return 200 OK with the weather data
    }
}
