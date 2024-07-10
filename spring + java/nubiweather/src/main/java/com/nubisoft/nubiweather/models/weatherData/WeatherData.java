package com.nubisoft.nubiweather.models.weatherData;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@Document(collection = "weatherData")
public class WeatherData {

    @Id
    private String id;

    @JsonProperty("location")
    private Location location;

    @JsonProperty("current")
    private Weather currentWeather;


    @Data
    public static class Location {
        
        @JsonProperty("name")
        private String name;

        @JsonProperty("country")
        private String country;
    }

    @Data 
    public static class Weather {

        @JsonProperty("temp_c")
        private double temp_c;

        @JsonProperty("condition")
        private Condition condition;

        @Data 
        public static class Condition  {

        @JsonProperty("text")
        private String text;

        }
    }
}
