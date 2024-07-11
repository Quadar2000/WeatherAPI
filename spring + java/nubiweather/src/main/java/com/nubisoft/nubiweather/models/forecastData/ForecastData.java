package com.nubisoft.nubiweather.models.forecastData;

import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Document(collection = "forecastData")
@NoArgsConstructor
@AllArgsConstructor
public class ForecastData {

    @Id
    private String id;

    @JsonProperty("location")
    private Location location;

    public ForecastData(Location location) {
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
    @JsonProperty("forecast")
    private Forecast forecast;

    @Data
    public static class Forecast {

        @JsonProperty("forecastday")
        private List<ForecastDay> forecastDay;

        @Data
        public static class ForecastDay {

            @JsonProperty("date")
            private String date;

            @JsonProperty("day")
            private Day day;

            @Data
            public static class Day {

                @JsonProperty("maxtemp_c")
                private double maxTempC;

                @JsonProperty("mintemp_c")
                private double minTempC;

                @JsonProperty("condition")
                private Condition condition;

                @Data
                public static class Condition {

                    @JsonProperty("text")
                    private String text;
                }
            }
        }
    }
}
