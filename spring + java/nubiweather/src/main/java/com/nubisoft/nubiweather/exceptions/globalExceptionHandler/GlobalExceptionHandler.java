package com.nubisoft.nubiweather.exceptions.globalExceptionHandler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.nubisoft.nubiweather.exceptions.ForecastDataNotFoundException.ForecastDataNotFoundException;
import com.nubisoft.nubiweather.exceptions.WeatherDataNotFoundException.WeatherDataNotFoundException;
import com.nubisoft.nubiweather.exceptions.dataRetrievalException.DataRetrievalException;
import com.nubisoft.nubiweather.exceptions.invalidForecastRequestException.InvalidForecastRequestException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({WeatherDataNotFoundException.class,ForecastDataNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> handleDataNotFoundException(WeatherDataNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidForecastRequestException.class)
    public ResponseEntity<Map<String, String>> handleInvalidForecastRequestException(InvalidForecastRequestException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataRetrievalException.class)
    public ResponseEntity<Map<String, String>> handleDataRetrievalException(DataRetrievalException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
}
