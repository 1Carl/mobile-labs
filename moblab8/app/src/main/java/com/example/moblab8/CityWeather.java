package com.example.moblab8;

import android.annotation.SuppressLint;
import android.icu.util.Calendar;
import android.os.AsyncTask;
import android.os.Build;
import android.util.JsonReader;

import androidx.annotation.RequiresApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.SimpleDateFormat;

public class CityWeather extends AsyncTask<String, Void, WeatherList>{
    private final String url;
    @SuppressLint("StaticFieldLeak")
    public MainActivity activity;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected WeatherList doInBackground(String... strings) {
        InputStream is;

        WeatherList wlist = new WeatherList();
        try {
            URL url = new URL(this.url);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(10000 /* milliseconds */);
            connection.setConnectTimeout(15000 /* milliseconds */);
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.connect();
            is = connection.getInputStream();
            BufferedReader streamReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            StringBuilder responseStrBuilder = new StringBuilder();

            String inputStr;
            while ((inputStr = streamReader.readLine()) != null)
                responseStrBuilder.append(inputStr);

            System.out.println(responseStrBuilder);
            JSONObject obj = new JSONObject(responseStrBuilder.toString());
            JSONObject main_obj = obj.getJSONObject("main");
            JSONArray jsonArray = obj.getJSONArray("weather");
            JSONObject obj0 = jsonArray.getJSONObject(0);
            wlist.setCt(obj.getString("name"));
            wlist.setDesc(obj0.getString("description"));
            wlist.setTmp(Float.parseFloat(main_obj.getString("temp")));
            Calendar calendar = Calendar.getInstance();
            @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("EEEE, MM-dd");
            String formatted_date = sdf.format(calendar.getTime());
            wlist.setDay(formatted_date);
            is.close();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return wlist ;
    }

    protected void onPostExecute(WeatherList t) {
        activity.sCallBackData(t);
    }

    public CityWeather(MainActivity activity, String url) {
            this.activity = activity;
            this.url = url;
        }

    }
