package com.example.moblab8;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

import com.example.moblab8.MainActivity;
import com.example.moblab8.WeatherList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class FiveDaysTask extends AsyncTask<String, Void, WeatherList>{
    private String url;
    int j;
    @SuppressLint("StaticFieldLeak")
    public MainActivity activity;
    @Override
    protected WeatherList doInBackground(String... strings) {
        InputStream is;
        WeatherList temp = new WeatherList();
        try {
            URL url = new URL(this.url);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(10000 /* milliseconds */);
            connection.setConnectTimeout(15000 /* milliseconds */);
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.connect();
            is = connection.getInputStream();
            XmlPullParserFactory xmlFactoryObject = XmlPullParserFactory.newInstance();
            XmlPullParser myParser = xmlFactoryObject.newPullParser();
            myParser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            myParser.setInput(is, null);
            temp = parseXML(myParser);
            is.close();

        } catch (IOException | XmlPullParserException e) {
            e.printStackTrace();
        }
        return temp;
    }

    public WeatherList parseXML(XmlPullParser myParser) {

        int event;
        int i = 0;
        WeatherList t = new WeatherList();

        try {
            event = myParser.getEventType();
            while (event != XmlPullParser.END_DOCUMENT) {
                String name = myParser.getName();

                switch (event) {
                    case XmlPullParser.START_TAG:
                        if(name.equals("name")){
                            t.setCt(myParser.nextText());
                        }
                        if(name.equals("time")){
                            i++;
                            if(j == i)
                                t.setDay(myParser.getAttributeValue(null, "from"));
                        }
                        if (name.equals("temperature") && j==i) {
                                t.setTmp(Float.parseFloat(myParser.getAttributeValue(null, "value"))-273);
                        }
                        if(name.equals("symbol") && j==i){
                            t.setDesc(myParser.getAttributeValue(null, "name"));
                        }

                        break;
                }
                event = myParser.next();
            }
            return t;

        } catch (Exception e) {
            e.printStackTrace();
            return t;
        }

    }

    protected void onPostExecute(WeatherList t) {
       activity.fCallBackData(t);
    }


    public FiveDaysTask(MainActivity activity, String url, Integer j) {
        this.activity = activity;
        this.url = url;
        this.j = j;
    }

}
