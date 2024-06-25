package com.nubisoft.nubiweather.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.nubisoft.nubiweather.models.ForecastData;
import com.nubisoft.nubiweather.models.WeatherData;
import com.nubisoft.nubiweather.services.ForecastRequest;
import com.nubisoft.nubiweather.services.WeatherService;

import java.util.ArrayList;
import java.util.List;

@RestController
public class WeatherController {

    @Autowired 
    WeatherService weatherService;

    @GetMapping("/realtime-weather")
    public List<WeatherData> getRealtimeWeather() {
    	String[] cities = {"Gliwice", "Hamburg"}; 
        List<WeatherData> weatherDataList = new ArrayList<>();

        for (String city : cities) {
            WeatherData weatherData = weatherService.getWeatherData(city);
            weatherDataList.add(weatherData);
        }
        return weatherDataList;
    }

    @PostMapping("/forecast-weather")
    public List<ForecastData> getForecastWeather(@RequestBody ForecastRequest forecastRequest) {
    	String[] cities = {"Gliwice", "Hamburg"}; 
        List<ForecastData> forecastDataList = new ArrayList<>();

        for (String city : cities) {
            ForecastData forecastData = weatherService.getForecastData(city, forecastRequest.getNumDays());
            forecastDataList.add(forecastData);
        }
        return forecastDataList;
    }

    @GetMapping("/weather-data")
    public List<WeatherData> getWeatherData() {
        return weatherService.getAllWeatherData();
    }

    @GetMapping("/forecast-data")
    public List<ForecastData> getForecastrData() {
        return weatherService.getAllForecastData();
    }
}