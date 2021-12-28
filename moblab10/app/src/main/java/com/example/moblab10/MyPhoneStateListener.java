package com.example.moblab10;

import android.annotation.SuppressLint;
import android.content.Context;
import android.telephony.CellLocation;
import android.telephony.PhoneStateListener;
import android.telephony.ServiceState;

import static com.example.moblab10.FileOperations.*;

import java.io.File;
public class MyPhoneStateListener extends PhoneStateListener {
    private Context context;
    private static final String fileName = android.os.Environment.getExternalStorageDirectory().getPath()+ File.separatorChar+"phonestate.txt";

    public MyPhoneStateListener(Context context) {
        this.context = context;
    }


    public void onCallStateChanged(int state, String incomingNumber) {
        writeToFile(fileName, "callstatechange: state: " + state + ", incomingNumber: " + incomingNumber);
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onCallForwardingIndicatorChanged(boolean cfi) {
        writeToFile(fileName, "callforwardIndicator: cfi: " + cfi);
        super.onCallForwardingIndicatorChanged(cfi);
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onCellLocationChanged(CellLocation location) {
        writeToFile(fileName, "CellLocationChanged: location:" + location);
        super.onCellLocationChanged(location);
    }

    @Override
    public void onDataConnectionStateChanged(int state, int networkType) {
        writeToFile(fileName, "ondataconnectionstate: state: " + state + ",networkType: " + networkType);
        super.onDataConnectionStateChanged(state, networkType);
    }

    @Override
    public void onServiceStateChanged(ServiceState serviceState) {
        writeToFile(fileName, "onServiceStateChanged: servicestate: " + serviceState);
        super.onServiceStateChanged(serviceState);
    }
}
