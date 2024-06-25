package com.nubisoft.nubiweather.Repositories;

import com.nubisoft.nubiweather.models.WeatherData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeatherDataRepository extends MongoRepository<WeatherData, String> {
}
