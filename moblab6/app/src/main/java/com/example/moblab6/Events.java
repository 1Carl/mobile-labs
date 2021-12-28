package com.example.moblab6;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.provider.ContactsContract;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class Events extends AppCompatActivity {
    private ListView lstNames;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.listview);
        super.onCreate(savedInstanceState);
        // Check the SDK version and whether the permission is already granted or not.
        // Find the list view
        this.lstNames = (ListView) findViewById(R.id.list);

        // Read and show the contacts
        showEvents();
    }

    private void showEvents() {
        // Check the SDK version and whether the permission is already granted or not.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CALENDAR}, 100);
            //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method
        } else {
            // Android version is lesser than 6.0 or the permission is already granted.
            List<Contact> contacts = getEvents();

            MyAdapter adapter = new MyAdapter(this, contacts);
            lstNames.setAdapter(adapter);
        }
    }

    private List<Contact> getEvents() {
        List<Contact> contacts = new ArrayList<>();
        // Get the ContentResolver
        ContentResolver cr = getContentResolver();
        Cursor cur = cr.query(CalendarContract.Events.CONTENT_URI,
                null, null, null, null);

        if ((cur != null ? cur.getCount() : 0) > 0) {
            while (cur != null && cur.moveToNext()) {
                String phoneNumber = cur.getString(cur.getColumnIndex(
                        CalendarContract.Events.DTSTART));
                String name = cur.getString(
                        cur.getColumnIndex(CalendarContract.Events.DESCRIPTION));


                    contacts.add(new Contact(name, phoneNumber));
            }
        }
        if(cur!=null){
            cur.close();
        }

        return contacts;
    }
}
