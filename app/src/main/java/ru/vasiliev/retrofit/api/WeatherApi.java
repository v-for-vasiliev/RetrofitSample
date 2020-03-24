package ru.vasiliev.retrofit.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import ru.vasiliev.retrofit.entity.WeatherNow;

public interface WeatherApi {

    // https://api.openweathermap.org/data/2.5/weather?lat=55.75&lon=37.62&appid=bd8089fc30abac8952def99cf86ad6d9

    @GET("data/2.5/weather")
    Call<WeatherNow> loadWeatherNow(@Query("lat") String latitude, @Query("lon") String longitude, @Query("appid") String appid);
}
