package com.nubisoft.nubiweather.weatherServiceTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.client.RestTemplate;

import com.nubisoft.nubiweather.NubiweatherApplication;
import com.nubisoft.nubiweather.Repositories.forecastDataRepository.ForecastDataRepository;
import com.nubisoft.nubiweather.Repositories.weatherDataRepository.WeatherDataRepository;
import com.nubisoft.nubiweather.exceptions.ForecastDataNotFoundException.ForecastDataNotFoundException;
import com.nubisoft.nubiweather.exceptions.WeatherDataNotFoundException.WeatherDataNotFoundException;
import com.nubisoft.nubiweather.models.forecastData.ForecastData;
import com.nubisoft.nubiweather.models.weatherData.WeatherData;
import com.nubisoft.nubiweather.services.weatherService.WeatherService;
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

        assertNotNull(weatherData);
        verify(weatherDataRepository, times(1)).save(mockWeatherData);
    }

    @Test
    public void testGetWeatherData_Failure() {

        WeatherData mockWeatherData = null;
        when(restTemplate.getForObject(anyString(), eq(WeatherData.class))).thenReturn(mockWeatherData);

        assertThrows(WeatherDataNotFoundException.class, () -> {
            weatherService.getWeatherData("Gliwice");
        });

    }

    @Test
    public void testGetForecastData_Success() {

        ForecastData mockForecastData = new ForecastData();

        when(restTemplate.getForObject(anyString(), eq(ForecastData.class))).thenReturn(mockForecastData);

        ForecastData forecastData = weatherService.getForecastData("Gliwice", 5);

        assertNotNull(forecastData);
        verify(forecastDataRepository, times(1)).save(mockForecastData);
    }

    @Test
    public void testGetForecastData_Failure() {

        ForecastData mockForecastData = null;
        when(restTemplate.getForObject(anyString(), eq(ForecastData.class))).thenReturn(mockForecastData);

        assertThrows(ForecastDataNotFoundException.class, () -> {
            weatherService.getForecastData("Gliwice", 5);
        });
    }

}
