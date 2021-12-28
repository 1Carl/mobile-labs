package com.example.moblab8;

import android.os.AsyncTask;
import android.os.Build;
import androidx.annotation.RequiresApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class EveryThreeHourWeather extends AsyncTask<String, Void, WeatherList>{
    private String url;
    public MainActivity activity;
    int k;
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

            JSONObject obj = new JSONObject(responseStrBuilder.toString());
            JSONObject city = obj.getJSONObject("city");
            JSONArray larray = obj.getJSONArray("list");

                JSONObject o = larray.getJSONObject(k);
                JSONObject main = o.getJSONObject("main");
                JSONArray weather = o.getJSONArray("weather");
                JSONObject desc = weather.getJSONObject(0);
                wlist.setDesc(desc.getString("description"));
                wlist.setTmp(Float.parseFloat(main.getString("temp")));
                wlist.setDay(o.getString("dt_txt"));
                wlist.setCt(city.getString("name"));

                is.close();

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return wlist;
    }

    protected void onPostExecute(WeatherList t) {
        activity.tCallBackData(t);
    }

    public EveryThreeHourWeather(MainActivity activity, String url, Integer integer) {
        this.activity = activity;
        this.url = url;
        this.k = integer;
    }

}
