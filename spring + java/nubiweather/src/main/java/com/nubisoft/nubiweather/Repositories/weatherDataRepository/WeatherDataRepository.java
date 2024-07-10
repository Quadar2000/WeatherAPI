package com.nubisoft.nubiweather.Repositories.weatherDataRepository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.nubisoft.nubiweather.models.weatherData.WeatherData;

@Repository
public interface WeatherDataRepository extends MongoRepository<WeatherData, String> {
}
