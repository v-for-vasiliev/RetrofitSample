package ru.vasiliev.retrofit;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import ru.vasiliev.retrofit.entity.WeatherNow;
import ru.vasiliev.retrofit.network.NetworkEngine;

public class MainActivity extends AppCompatActivity implements WeatherReceiver {

    private Button getButton;
    private TextView output;

    private NetworkEngine networkEngine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        networkEngine = new NetworkEngine();

        getButton = findViewById(R.id.get_button);
        getButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                networkEngine.getWeatherNow("55.75", "37.62", MainActivity.this);
            }
        });

        output = findViewById(R.id.response);
    }

    @Override
    public void onWeatherReceived(final WeatherNow weatherNow) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                output.setText(weatherNow.getName());
            }
        });
    }

    @Override
    public void onRequestError() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                output.setText("Ошибка");
            }
        });
    }
}
