package com.example.moblab6;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class MyAdapter extends BaseAdapter {
    Context context;
    List<Contact> rowData;
    public MyAdapter(Context context, List<Contact> items) {
        this.context = context;
        this.rowData = items;
    }
    /*private view holder class*/
    private class ViewHolder {
        TextView txtTitle;
        TextView txtDesc;
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        LayoutInflater mInflater = (LayoutInflater)
                context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.contact, null);
            holder = new ViewHolder();
            holder.txtDesc = (TextView) convertView.findViewById(R.id.subtitle);
            holder.txtTitle = (TextView) convertView.findViewById(R.id.title);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }
        Contact rowItem = (Contact) getItem(position);
        holder.txtDesc.setText(rowItem.getNumber());
        holder.txtTitle.setText(rowItem.getName());
        return convertView;
    }
    @Override
    public int getCount() {
        return rowData.size();
    }
    @Override
    public Object getItem(int position) {
        return rowData.get(position);
    }
    @Override
    public long getItemId(int position) {
        return rowData.indexOf(getItem(position));
    }
}

