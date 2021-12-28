package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

public class TLayout extends AppCompatActivity {
    RadioButton rb1, rb2;
    TimePicker tp;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tlayout);
        Intent i = getIntent();
        rb1 = findViewById(R.id.rb1);
        rb2 = findViewById(R.id.rb2);
        tp = findViewById(R.id.tp);
        rb1.setChecked(i.getBooleanExtra("rb1", false));
        rb2.setChecked(i.getBooleanExtra("rb2", false));
        this.tp.setHour(i.getIntExtra("tl_tp_hour", 0));
        this.tp.setMinute(i.getIntExtra("tl_tp_min", 0));
    }

    public void buttonClicked(View v) {
        if(v.getId() == R.id.ok) {
            Intent i = new Intent();
            i.putExtra("tl_tp_hour", tp.getHour());
            i.putExtra("tl_tp_min", tp.getMinute());
            i.putExtra("rb1", rb1.isChecked());
            i.putExtra("rb2", rb2.isChecked());
            setResult(RESULT_OK, i);
        }
        else {
            setResult(RESULT_CANCELED);
        }
        finish();
    }
}
