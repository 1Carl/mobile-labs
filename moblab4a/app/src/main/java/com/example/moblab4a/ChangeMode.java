package com.example.moblab4a;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

public class ChangeMode extends AppCompatActivity {
    int mode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mode);
        Intent i = getIntent();
        mode = i.getIntExtra("mode", 3);
        switch (mode) {
            case 1:
                ((RadioButton) findViewById(R.id.rb1)).setChecked(true);
                break;
            case 2:
                ((RadioButton) findViewById(R.id.rb2)).setChecked(true);
                break;
            case 3:
                ((RadioButton) findViewById(R.id.rb3)).setChecked(true);
                break;
        }

    }


    @SuppressLint("ApplySharedPref")
    public void btnclicked(View view) {
        if(view.getId() == R.id.ok) {
            if (((RadioButton) findViewById(R.id.rb1)).isChecked()) {
                mode = 1;
            }
            if (((RadioButton) findViewById(R.id.rb2)).isChecked()) {
                mode = 2;
            }
            if (((RadioButton) findViewById(R.id.rb3)).isChecked()) {
                mode = 3;
            }
        }
        Intent i = new Intent();
        i.putExtra("mode", mode);
        setResult(1, i);
        finish();
    }
}
