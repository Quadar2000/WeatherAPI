package com.nubisoft.nubiweather.Repositories;

import com.nubisoft.nubiweather.models.ForecastData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ForecastDataRepository extends MongoRepository<ForecastData, String> {
}
