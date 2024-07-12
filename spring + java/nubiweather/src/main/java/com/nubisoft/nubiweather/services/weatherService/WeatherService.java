package com.nubisoft.nubiweather.services.weatherService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.nubisoft.nubiweather.Repositories.forecastDataRepository.ForecastDataRepository;
import com.nubisoft.nubiweather.Repositories.weatherDataRepository.WeatherDataRepository;
import com.nubisoft.nubiweather.exceptions.ForecastDataNotFoundException.ForecastDataNotFoundException;
import com.nubisoft.nubiweather.exceptions.WeatherDataNotFoundException.WeatherDataNotFoundException;
import com.nubisoft.nubiweather.models.forecastData.ForecastData;
import com.nubisoft.nubiweather.models.weatherData.WeatherData;

import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {

    @Value("${weatherapi.key}")
    private String apiKey;

    @Value("${weatherapi.url}")
    private String weatherApiUrl;

    @Value("${forecastapi.url}")
    private String forecastApiUrl;

    @Autowired
    public RestTemplate restTemplate;

    @Autowired
    private WeatherDataRepository weatherDataRepository;

    @Autowired
    private ForecastDataRepository forecastDataRepository;

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public void setWeatherApiUrl(String weatherApiUrl) {
        this.weatherApiUrl = weatherApiUrl;
    }

    public void setForecastApiUrl(String forecastApiUrl) {
        this.forecastApiUrl = forecastApiUrl;
    }

    public WeatherData getWeatherData(String city) {
        try {
            String url = String.format(weatherApiUrl, apiKey, city);
            WeatherData weatherData = restTemplate.getForObject(url, WeatherData.class);
            if (weatherData == null) {
                throw new WeatherDataNotFoundException("Weather data not found for city: " + city);
            }
            weatherDataRepository.save(weatherData);
            return weatherData;
        } catch (HttpClientErrorException e) {
            throw new WeatherDataNotFoundException("Weather data not found for city: " + city);
        }
    }

    public ForecastData getForecastData(String city, Integer numDays) {
        String url = String.format(forecastApiUrl, apiKey, city, numDays);
        ForecastData forecastData = restTemplate.getForObject(url, ForecastData.class);
        if (forecastData == null) {
            throw new ForecastDataNotFoundException("Forecast data not found for city: " + city);
        }
        forecastDataRepository.save(forecastData);
        return forecastData;
    }

    public List<WeatherData> getAllWeatherData() {
        return weatherDataRepository.findAll();
    }

    public List<ForecastData> getAllForecastData() {
        return forecastDataRepository.findAll();
    }
}