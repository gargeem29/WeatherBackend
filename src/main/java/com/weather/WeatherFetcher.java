package com.weather;


import java.net.URI;
import java.net.http.HttpClient;
 import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


@SpringBootApplication
public class WeatherFetcher {
    private static final String API_KEY = "14f822bfb6925ccb893161e06145ac98";
    private static final String CITY = "Delhi";
    
    public static void main(String[] args) throws Exception {
        // Create the API URL
        String url = String.format("https://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s", CITY, API_KEY);

        // Create HttpClient and make request
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        // Send the request and get the response
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // Parse JSON response
        JsonObject jsonResponse = JsonParser.parseString(response.body()).getAsJsonObject();
        double tempKelvin = jsonResponse.getAsJsonObject("main").get("temp").getAsDouble();
        double tempCelsius = tempKelvin - 273.15;

        System.out.println("Current temperature in Celsius: " + tempCelsius);
    }
}
