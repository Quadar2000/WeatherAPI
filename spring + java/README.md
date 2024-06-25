
## features

This application implements folowing features:
- reading current weather data for Gliwice and Hamburg
- reading forecast for Gliwice and Hamburg, for few days forward, number of days based on value given in request
- every read is writen to Mongo DB Atlas database
- previous readings can be downloaded from database(use endpoints: /weather-data or /forecast-data)

## how to use application?

Application is configured for authorize requests from http://localhost:3000. Requests from another domains will be rejected.
You can use tools as Postman to test application (which is recomended as fastest method), but make sure you use localhost:3000 in your tool. 

How to acces data, using request methods:

- reading current weather:  GET http://localhost:8082/realtime-weather
- reading forecast: POST http://localhost:8082/forecast-weather (this method have to containe in body value for numDays)
- read previous current weather readings from database: GET http://localhost:8082/weather-data
- read previous forecasts readings from database: GET http://localhost:8082/forecast-data

If there are eny problems with port 8082 used by application, it may be changed in file application.properties