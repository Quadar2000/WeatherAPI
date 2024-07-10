package com.nubisoft.nubiweather.Repositories.forecastDataRepository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.nubisoft.nubiweather.models.forecastData.ForecastData;

@Repository
public interface ForecastDataRepository extends MongoRepository<ForecastData, String> {
}
