package com.nubisoft.nubiweather.weatherController;

import com.nubisoft.nubiweather.controllers.weatherController.WeatherController;
import com.nubisoft.nubiweather.models.forecastData.ForecastData;
import com.nubisoft.nubiweather.models.weatherData.WeatherData;
import com.nubisoft.nubiweather.services.weatherService.WeatherService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.http.MediaType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(WeatherController.class)
public class WeatherControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WeatherService weatherService;

    @Test
    public void testGetRealtimeWeather() throws Exception {
        WeatherData weatherData1 = new WeatherData();
        WeatherData.Location location1 = new WeatherData.Location();
        location1.setName("Gliwice");
        location1.setCountry("Poland");
        weatherData1.setLocation(location1);
        
        WeatherData weatherData2 = new WeatherData();
        WeatherData.Location location2 = new WeatherData.Location();
        location2.setName("Hamburg");
        location2.setCountry("Germany");
        weatherData2.setLocation(location2);

        List<WeatherData> weatherDataList = new ArrayList<>();
        weatherDataList.add(weatherData1);
        weatherDataList.add(weatherData2);

        when(weatherService.getWeatherData("Gliwice")).thenReturn(weatherData1);
        when(weatherService.getWeatherData("Hamburg")).thenReturn(weatherData2);

        mockMvc.perform(get("/realtime-weather")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].location.name").value("Gliwice"))
                .andExpect(jsonPath("$[0].location.country").value("Poland"))
                .andExpect(jsonPath("$[1].location.name").value("Hamburg"))
                .andExpect(jsonPath("$[1].location.country").value("Germany"));
    }

    @Test
    public void testGetForecastWeather() throws Exception {
        ForecastData forecastData1 = new ForecastData();
        ForecastData.Location location1 = new ForecastData.Location();
        location1.setName("Gliwice");
        location1.setCountry("Poland");
        forecastData1.setLocation(location1);

        ForecastData forecastData2 = new ForecastData();
        ForecastData.Location location2 = new ForecastData.Location();
        location2.setName("Hamburg");
        location2.setCountry("Germany");
        forecastData2.setLocation(location2);

        when(weatherService.getForecastData("Gliwice", 5)).thenReturn(forecastData1);
        when(weatherService.getForecastData("Hamburg", 5)).thenReturn(forecastData2);

        String forecastRequestJson = "{\"numDays\": 5}";

        mockMvc.perform(post("/forecast-weather")
                .contentType(MediaType.APPLICATION_JSON)
                .content(forecastRequestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].city").value("Gliwice"))
                .andExpect(jsonPath("$[1].city").value("Hamburg"));
    }
    
}
