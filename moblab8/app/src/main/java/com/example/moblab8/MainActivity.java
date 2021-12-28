package com.example.moblab8;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView fl, sl, tl;
    Button btn, ctsnow, hourly;
    int i;
    ArrayList<WeatherList> ftemps, stemps, ttemps;
    WeatherAdapter fadapter, sadapter, tadapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ftemps = new ArrayList<>();
        fadapter = new WeatherAdapter(this, R.layout.item_user, ftemps );
        stemps = new ArrayList<>();
        sadapter = new WeatherAdapter(this, R.layout.item_user, stemps );
        ttemps = new ArrayList<>();
        tadapter = new WeatherAdapter(this, R.layout.item_user, ttemps );
        fl = findViewById(R.id.flist);
        sl = findViewById(R.id.slist);
        tl = findViewById(R.id.tlist);
        btn = findViewById(R.id.ok);
        ctsnow = findViewById(R.id.citiesnow);
        hourly = findViewById(R.id.hrs);

        btn.setOnClickListener(v -> {
            for(i=1; i<=5; i++) {
                fiveDaysWeather(i*8);
            }
            fl.setAdapter(fadapter);
        });
        ctsnow.setOnClickListener(v -> {
            cityWeather("Ulaanbaatar");
            cityWeather("London");
            cityWeather("Tokyo");
            cityWeather("Beijing");
            cityWeather("Moscow");
            sl.setAdapter(sadapter);
        });
        hourly.setOnClickListener(v -> {
            for(int counter = 0; counter<8; counter++)
                threeHourWeather(counter);
            tl.setAdapter(tadapter);
        });

    }
    public void fCallBackData(WeatherList t) {
        ftemps.add(t);
        fadapter.notifyDataSetChanged();
    }
    public void sCallBackData(WeatherList t) {
        stemps.add(t);
        sadapter.notifyDataSetChanged();
    }
    public void tCallBackData(WeatherList t) {
        ttemps.add(t);
        tadapter.notifyDataSetChanged();
    }

    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    public void threeHourWeather(int j){
        String urlstring = "https://api.openweathermap.org/data/2.5/forecast?q=Ulaanbaatar&appid=bb4f29c82622269cdae24ead4e02e7a4";
        EveryThreeHourWeather a = new EveryThreeHourWeather(this, urlstring, j);
        a.execute();
    }

    public void fiveDaysWeather(Integer j) {
        String urlstring = "https://api.openweathermap.org/data/2.5/forecast?q=Ulaanbaatar&mode=xml&appid=bb4f29c82622269cdae24ead4e02e7a4";
        FiveDaysTask a = new FiveDaysTask(this, urlstring, j);
        a.execute();
    }

    public void cityWeather(String c){
        String urlstring = String.format("https://api.openweathermap.org/data/2.5/weather?q=%s&appid=bb4f29c82622269cdae24ead4e02e7a4", c);
        CityWeather a = new CityWeather(this, urlstring);
        a.execute();
    }
}
