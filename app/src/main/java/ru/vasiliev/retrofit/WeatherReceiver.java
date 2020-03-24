package ru.vasiliev.retrofit;

import ru.vasiliev.retrofit.entity.WeatherNow;

public interface WeatherReceiver {

    void onWeatherReceived(WeatherNow weatherNow);

    void onRequestError();
}
