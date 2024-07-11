package com.nubisoft.nubiweather.weatherController;

import com.nubisoft.nubiweather.controllers.weatherController.WeatherController;
import com.nubisoft.nubiweather.models.forecastData.ForecastData;
import com.nubisoft.nubiweather.models.weatherData.WeatherData;
import com.nubisoft.nubiweather.services.weatherService.WeatherService;
import com.nubisoft.nubiweather.testSecurityConfig.TestSecurityConfig;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(WeatherController.class)
@Import(TestSecurityConfig.class)
public class WeatherControllerTest {

    private final WeatherData.Location weatherLocation1 = new WeatherData.Location("Gliwice","Poland");

    private final WeatherData.Location weatherLocation2 = new WeatherData.Location("Hamburg","Germany");

    private final WeatherData weatherData1 = new WeatherData(weatherLocation1);

    private final WeatherData weatherData2 = new WeatherData(weatherLocation2 );

    private final ForecastData.Location forecastLocation1 = new ForecastData.Location("Gliwice","Poland");

    private final ForecastData.Location forecastLocation2 = new ForecastData.Location("Hamburg","Germany");

    private final ForecastData forecastData1 = new ForecastData(forecastLocation1);

    private final ForecastData forecastData2 = new ForecastData(forecastLocation2);
    

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WeatherService weatherService;

    @Test
    public void testGetRealtimeWeather() throws Exception {

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

        when(weatherService.getForecastData("Gliwice", 5)).thenReturn(forecastData1);
        when(weatherService.getForecastData("Hamburg", 5)).thenReturn(forecastData2);

        String forecastRequestJson = "{\"numDays\": 5}";

        mockMvc.perform(post("/forecast-weather")
                .contentType(MediaType.APPLICATION_JSON)
                .content(forecastRequestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].location.name").value("Gliwice"))
                .andExpect(jsonPath("$[1].location.name").value("Hamburg"));
    }

    @Test
    public void testGetForecastWeatherInvalidParameter() throws Exception {

        String forecastRequestJson = "{\"numDays\": 0}";

        mockMvc.perform(post("/forecast-weather")
                .contentType(MediaType.APPLICATION_JSON)
                .content(forecastRequestJson))
                .andExpect(status().isBadRequest());
    }
    
}
