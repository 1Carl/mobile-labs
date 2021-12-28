package com.example.moblab10;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

public class SmsBroadcastReceiver extends BroadcastReceiver {

    public static final String SMS_BUNDLE = "pdus";

    public void onReceive(Context context, Intent intent) {
        Bundle intentExtras = intent.getExtras();
        if (intentExtras != null) {
            Object[] sms = (Object[]) intentExtras.get(SMS_BUNDLE);
            StringBuilder smsMessageStr = new StringBuilder();
            for (Object sm : sms) {
                String format = intentExtras.getString("format");
                SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) sm, format);

                String smsBody = smsMessage.getMessageBody().toString();
                String address = smsMessage.getOriginatingAddress();

                smsMessageStr.append("SMS From: ").append(address).append("\n");
                smsMessageStr.append(smsBody).append("\n");
            }

            MessageActivity inst = MessageActivity.instance();
            inst.updateInbox(smsMessageStr.toString());
        }
    }
}
