package com.nubisoft.nubiweather.other.forecastRequest;

//import jakarta.validation.constraints.constraints.Min;
//import jakarta.validation.constraints.constraints.NotNull;

public class ForecastRequest {

    //@NotNull(message = "Number of days cannot be null")
    //@Min(value = 1, message = "Number of days must be at least 1")
    private int numDays;

    public int getNumDays() {
        return numDays;
    }

}
