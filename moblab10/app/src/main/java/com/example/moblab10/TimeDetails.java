package com.example.moblab10;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import static com.example.moblab10.FileOperations.*;

import java.util.Calendar;

public class TimeDetails extends BroadcastReceiver {
    String filename;
    @Override
    public void onReceive(Context context, Intent intent)
    {
        Calendar current = Calendar.getInstance();
        if(intent.getAction().equals(Intent.ACTION_TIME_TICK) && current.get(Calendar.MINUTE) % 5 == 0)
            writeToFile(filename, current.getTime().toString());
        if(intent.getAction().equals(Intent.ACTION_TIME_CHANGED))
            writeToFile(filename, current.getTime().toString());
    }

    public TimeDetails(String filename) {
        this.filename = filename;
    }
}