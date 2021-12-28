package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;

import androidx.appcompat.app.AppCompatActivity;

public class LLayout extends AppCompatActivity {

    private CheckBox cb1, cb2, cb3;
    private DatePicker d;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.llayout);
        d = findViewById(R.id.dp);
        cb1 = findViewById(R.id.cb1);
        cb2 = findViewById(R.id.cb2);
        cb3 = findViewById(R.id.cb3);
        Intent i = getIntent();
        d.updateDate(i.getIntExtra("year", 1970),
                i.getIntExtra("month", 1),
                i.getIntExtra("day", 1));
        cb1.setChecked(i.getBooleanExtra("cb1", false));
        cb2.setChecked(i.getBooleanExtra("cb2", false));
        cb3.setChecked(i.getBooleanExtra("cb3", false));
    }

    public void buttonClicked(View v) {
        if(v.getId() == R.id.ok) {
            Intent i = new Intent();
            i.putExtra("year", d.getYear());
            i.putExtra("month", d.getMonth());
            i.putExtra("day", d.getDayOfMonth());
            i.putExtra("cb1", cb1.isChecked());
            i.putExtra("cb2", cb2.isChecked());
            i.putExtra("cb3", cb3.isChecked());
            setResult(RESULT_OK, i);
        }
        else {
            setResult(RESULT_CANCELED);
        }
        finish();
    }
}
