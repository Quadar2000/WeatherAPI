package com.nubisoft.nubiweather.models.weatherData;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Document(collection = "weatherData")
@NoArgsConstructor
@AllArgsConstructor
public class WeatherData {

    @Id
    private String id;

    @JsonProperty("location")
    private Location location;

    @JsonProperty("current")
    private Weather currentWeather;

    public WeatherData(Location location) {
        this.location = location;
    }

    @Data
    @NoArgsConstructor
    public static class Location {
        
        @JsonProperty("name")
        private String name;

        @JsonProperty("country")
        private String country;

        public Location(String name, String country) {
            this.name = name;
            this.country = country;
        }

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
