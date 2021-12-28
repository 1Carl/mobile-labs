package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

public class RLayout extends AppCompatActivity {
    RatingBar rb;
    TimePicker tp;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rlayout);
        this.rb = findViewById(R.id.rab);
        this.tp = findViewById(R.id.tp);
        Intent i = getIntent();
        this.rb.setRating(i.getFloatExtra("rl_rb", 0.0F));
        this.tp.setHour(i.getIntExtra("rl_tp_hour", 0));
        this.tp.setMinute(i.getIntExtra("rl_tp_min", 0));
    }

    public void buttonClicked(View v) {
        if(v.getId() == R.id.ok) {
            Intent i = new Intent();
            i.putExtra("rl_tp_hour", tp.getHour());
            i.putExtra("rl_rb", rb.getRating());
            i.putExtra("rl_tp_min", tp.getMinute());
            setResult(RESULT_OK, i);
        }
        else {
            setResult(RESULT_CANCELED);
        }
        finish();
    }
}
