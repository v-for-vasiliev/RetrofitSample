package ru.vasiliev.retrofit.network;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.vasiliev.retrofit.WeatherReceiver;
import ru.vasiliev.retrofit.api.WeatherApi;
import ru.vasiliev.retrofit.entity.WeatherNow;

public class NetworkEngine {

    public static final String BASE_URL = "https://api.openweathermap.org";

    public static final String APP_ID = "bd8089fc30abac8952def99cf86ad6d9";

    private WeatherApi weatherApi;

    public NetworkEngine() {
        // Клиент для сетевых запросов
        OkHttpClient client = new OkHttpClient.Builder().connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS).readTimeout(30, TimeUnit.SECONDS).build();

        // Клиент для HTTP запросов
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL) // Задаем базовый URL
                .client(client) // Задаем клиент для сетевых запросов
                .addConverterFactory(GsonConverterFactory.create()) // Задаем конвертер респонса из String в WeatherApi
                .build(); // "Билдим" наш ретрофит

        weatherApi = retrofit.create(WeatherApi.class);
    }

    // Асинхронный метод - вызывается и сразу отдает управлением следующим за вызовом этого метода строкам кода.
    public void getWeatherNow(String latitude, String longitude, final WeatherReceiver weatherReceiver) {
        Call<WeatherNow> call = weatherApi.loadWeatherNow(latitude, longitude, APP_ID);
        call.enqueue(new Callback<WeatherNow>() {
            @Override
            public void onResponse(Call<WeatherNow> call, Response<WeatherNow> response) {
                weatherReceiver.onWeatherReceived(response.body());
            }

            @Override
            public void onFailure(Call<WeatherNow> call, Throwable t) {
                weatherReceiver.onRequestError();
            }
        });
    }

    // Синхронный метод - код, вызывающий этот метод, будет ждать, пока он не завершится.
    public WeatherNow getWeatherNowSynch(String latitude, String longitude) throws IOException {
        Call<WeatherNow> call = weatherApi.loadWeatherNow(latitude, longitude, APP_ID);
        Response<WeatherNow> response = call.execute();
        return response.body();
    }

    public String getWeatherViaOkHttp(String url) throws IOException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();

        try (okhttp3.Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }
}
