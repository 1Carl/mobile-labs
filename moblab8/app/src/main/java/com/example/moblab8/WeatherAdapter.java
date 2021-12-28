package com.example.moblab8;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class WeatherAdapter extends ArrayAdapter<WeatherList> {
    private final Context mContext;
    int mResource;
    public WeatherAdapter(@NonNull Context context, int resource, @NonNull ArrayList<WeatherList> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @SuppressLint({"ViewHolder", "SetTextI18n"})
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String a = getItem(position).getCt();
        String b = getItem(position).getDay();
        String c = getItem(position).getDesc();
        Float t = getItem(position).getTmp();
        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);
        TextView t1 = convertView.findViewById(R.id.listcity);
        TextView t2 = convertView.findViewById(R.id.listdesc);
        TextView t3 = convertView.findViewById(R.id.listtmp);
        TextView t4 = convertView.findViewById(R.id.listday);
        t1.setText(a);
        t4.setText(b);
        t2.setText(c);
        t3.setText(t.toString());
        return convertView;
    }
}
