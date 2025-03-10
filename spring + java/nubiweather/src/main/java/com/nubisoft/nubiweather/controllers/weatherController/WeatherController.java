package com.nubisoft.nubiweather.controllers.weatherController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.nubisoft.nubiweather.exceptions.invalidForecastRequestException.InvalidForecastRequestException;
import com.nubisoft.nubiweather.models.forecastData.ForecastData;
import com.nubisoft.nubiweather.models.weatherData.WeatherData;
import com.nubisoft.nubiweather.other.forecastRequest.ForecastRequest;
import com.nubisoft.nubiweather.services.weatherService.WeatherService;

import jakarta.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.List;

@RestController
public class WeatherController {

    private final String[] cities = {"Gliwice", "Hamburg"}; 

    @Autowired 
    WeatherService weatherService;

    @GetMapping("/realtime-weather")
    public List<WeatherData> getRealtimeWeather() {
        List<WeatherData> weatherDataList = new ArrayList<>();
        for (String city : cities) {
            WeatherData weatherData = weatherService.getWeatherData(city);
            weatherDataList.add(weatherData);
        }
        return weatherDataList;
    }

    @PostMapping("/forecast-weather")
    public List<ForecastData> getForecastWeather( @RequestBody ForecastRequest forecastRequest,HttpServletResponse response) {

        if (forecastRequest.getNumDays() < 1 || forecastRequest.getNumDays() > 10 || forecastRequest.getNumDays() == null) {
            throw new InvalidForecastRequestException("Number of days can not be empty, lesser than 1 and greater than 10");
        }

        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        
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