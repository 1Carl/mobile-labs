package com.example.moblab10;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import static com.example.moblab10.FileOperations.*;

import static android.telephony.PhoneStateListener.*;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    public TelephonyManager tm;
    public MyPhoneStateListener mpsl;
    private String simfile = android.os.Environment.getExternalStorageDirectory().getPath()+ File.separatorChar+ "siminfo.txt";
    private String defFile = android.os.Environment.getExternalStorageDirectory().getPath()+ File.separatorChar+ "deviceinfo.txt";
    ChargingOnReceiver cr = null;
    TimeDetails td = null;
    private IntentFilter batteryIF, timeIF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        filePermissions();
        checkpermissions();
        this.tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        writeSimInfo();
        this.mpsl = new MyPhoneStateListener(this);
        tm.listen(mpsl,
                LISTEN_CALL_FORWARDING_INDICATOR|
                LISTEN_CALL_STATE|
                LISTEN_CELL_INFO|
                LISTEN_CELL_LOCATION|
                LISTEN_DATA_ACTIVITY|
                LISTEN_SERVICE_STATE
        );
        deviceInfoWriterStart();
    }

    public void deviceInfoWriterStart() {
        this.batteryIF = new IntentFilter();
        this.timeIF = new IntentFilter();
        td = new TimeDetails(defFile);
        cr = new ChargingOnReceiver(defFile);

        this.batteryIF.addAction(Intent.ACTION_BATTERY_CHANGED);
        this.batteryIF.addAction(Intent.ACTION_POWER_CONNECTED);
        this.batteryIF.addAction(Intent.ACTION_POWER_DISCONNECTED);
        this.batteryIF.addAction(Intent.ACTION_BOOT_COMPLETED);

        this.timeIF.addAction(Intent.ACTION_TIME_CHANGED);
        this.timeIF.addAction(Intent.ACTION_TIME_TICK);
        registerReceiver(td, timeIF);
        registerReceiver(cr, batteryIF);
    }

    @SuppressLint({"MissingPermission", "HardwareIds"})
    private void writeSimInfo() {
        writeToFile(simfile, "Phone Number: " + tm.getLine1Number(), false);
        writeToFile(simfile, "Country Iso: " + tm.getSimCountryIso());
        writeToFile(simfile, "Operator Code: " + tm.getSimOperator());
        writeToFile(simfile, "Operator Name: " + tm.getSimOperatorName());
        writeToFile(simfile, "Sim Serial: " + tm.getSimSerialNumber());
    }

    private void checkpermissions() {
        if (checkSelfPermission(Manifest.permission.READ_PHONE_NUMBERS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{
                    Manifest.permission.READ_PHONE_NUMBERS,
            }, 1);
            return;
        }
    }

    private void filePermissions() {
        if (ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        1);
            }
        }
    }

    public void onClick(View view) {
        Intent i = new Intent();
        switch (view.getId()) {
            case R.id.smswrite:
                i.setClass(this, MessageActivity.class);
                startActivity(i);
                return;
        }
    }
}