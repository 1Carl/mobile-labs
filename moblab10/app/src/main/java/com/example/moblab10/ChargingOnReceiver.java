package com.example.moblab10;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import java.io.File;
import static com.example.moblab10.FileOperations.*;

public class ChargingOnReceiver extends BroadcastReceiver {
    String filename;
    public ChargingOnReceiver(String fileName) {
        this.filename = fileName;
    }
    public void onReceive(Context context, Intent intent) {
        writeToFile(filename, intent.toString());
    }
}
