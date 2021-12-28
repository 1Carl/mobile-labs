package com.example.moblab6;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.main);
        super.onCreate(savedInstanceState);
    }


    public void btnclicked(View view) {
        Intent i = new Intent();
        switch (view.getId()) {
            case R.id.calendar:
                i.setClass(this, Events.class);
                break;
            case R.id.contact:
                i.setClass(this, Contacts.class);
                break;
            case R.id.lab4:
                i.setClass(this, Index.class);
        }

        startActivity(i);
    }
}
