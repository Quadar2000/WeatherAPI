package com.nubisoft.nubiweather.weatherServiceTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import com.nubisoft.nubiweather.NubiweatherApplication;
import com.nubisoft.nubiweather.Repositories.forecastDataRepository.ForecastDataRepository;
import com.nubisoft.nubiweather.Repositories.weatherDataRepository.WeatherDataRepository;
import com.nubisoft.nubiweather.models.forecastData.ForecastData;
import com.nubisoft.nubiweather.models.weatherData.WeatherData;
import com.nubisoft.nubiweather.services.weatherService.WeatherService;
import com.nubisoft.nubiweather.testSecurityConfig.TestSecurityConfig;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = {NubiweatherApplication.class})
public class WeatherServiceTest {

    @MockBean
    private RestTemplate restTemplate;

    @MockBean
    private WeatherDataRepository weatherDataRepository;

    @MockBean
    private ForecastDataRepository forecastDataRepository;

    @InjectMocks
    private WeatherService weatherService;

    @Value("${weatherapi.key}")
    private String apiKey;

    @Value("${weatherapi.url}")
    private String weatherApiUrl;

    @Value("${forecastapi.url}")
    private String forecastApiUrl;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        weatherService.setApiKey(apiKey);
        weatherService.setWeatherApiUrl(weatherApiUrl);
        weatherService.setForecastApiUrl(forecastApiUrl);
    }

    @Test
    public void testGetWeatherData_Success() {

        WeatherData mockWeatherData = new WeatherData();

        mockWeatherData.setLocation(new WeatherData.Location("Gliwice","Poland"));

        when(restTemplate.getForObject(anyString(), eq(WeatherData.class))).thenReturn(mockWeatherData);

        WeatherData weatherData = weatherService.getWeatherData("Gliwice");

        assertNotNull(weatherData.getLocation());
        verify(weatherDataRepository, times(1)).save(mockWeatherData);
    }

    @Test
    public void testGetWeatherData_Failure() {

        WeatherData mockWeatherData = new WeatherData();
        when(restTemplate.getForObject(anyString(), eq(WeatherData.class))).thenReturn(mockWeatherData);

        WeatherData weatherData = weatherService.getWeatherData("Gliwice");

        assertNull(weatherData.getLocation());
    }

    @Test
    public void testGetForecastData_Success() {

        ForecastData mockForecastData = new ForecastData();
        mockForecastData.setLocation(new ForecastData.Location("Gliwice","Poland"));

        when(restTemplate.getForObject(anyString(), eq(ForecastData.class))).thenReturn(mockForecastData);

        ForecastData forecastData = weatherService.getForecastData("Gliwice", 5);

        assertNotNull(forecastData.getLocation());
        verify(forecastDataRepository, times(1)).save(mockForecastData);
    }

    @Test
    public void testGetForecastData_Failure() {

        ForecastData mockForecastData = new ForecastData();
        when(restTemplate.getForObject(anyString(), eq(ForecastData.class))).thenReturn(mockForecastData);

        ForecastData forecastData = weatherService.getForecastData("Gliwice", 5);

        assertNull(forecastData.getLocation());
    }

}
