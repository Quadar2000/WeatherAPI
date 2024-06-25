package com.nubisoft.nubiweather.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.nubisoft.nubiweather.Repositories.ForecastDataRepository;
import com.nubisoft.nubiweather.Repositories.WeatherDataRepository;
import com.nubisoft.nubiweather.models.ForecastData;
import com.nubisoft.nubiweather.models.WeatherData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {

    //@Value("${weatherapi.key}")
	@Value("9113b879c4184822896122709242006")
    private String apiKey;

    @Autowired
    public RestTemplate restTemplate;

    @Autowired
    private WeatherDataRepository weatherDataRepository;

    @Autowired
    private ForecastDataRepository forecastDataRepository;

    private static final String WEATHER_API_URL = "http://api.weatherapi.com/v1/current.json?key=%s&q=%s&aqi=no";
    private static final String FORECAST_API_URL = "http://api.weatherapi.com/v1/forecast.json?key=%s&q=%s&days=%s&aqi=no&alerts=no";

    public WeatherData getWeatherData(String city) {
        String url = String.format(WEATHER_API_URL, apiKey, city);
        WeatherData weatherData = restTemplate.getForObject(url, WeatherData.class);
        if (weatherData != null) {
            weatherDataRepository.save(weatherData);
        }
        return weatherData;
    }
    
    public ForecastData getForecastData(String city, int numDays) {
        String url = String.format(FORECAST_API_URL, apiKey, city, numDays);
        ForecastData forecastData = restTemplate.getForObject(url, ForecastData.class);
        if (forecastData != null) {
            forecastDataRepository.save(forecastData);
        }
        return forecastData;
    }

    public List<WeatherData> getAllWeatherData() {
        return weatherDataRepository.findAll();
    }

    public List<ForecastData> getAllForecastData() {
        return forecastDataRepository.findAll();
    }
}