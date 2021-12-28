package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private Button a, s, m, d;
    private EditText val1, val2, result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.a = (Button) findViewById(R.id.btAdd);
        this.s = (Button) findViewById(R.id.btSub);
        this.m = (Button) findViewById(R.id.btMulti);
        this.d = (Button) findViewById(R.id.btDivide);
        this.val1 = (EditText) findViewById(R.id.etValueA);
        this.result = (EditText) findViewById(R.id.etResult);
        this.val2 = (EditText) findViewById(R.id.etValueB);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        String value1 = val1.getText().toString();
        String value2 = val2.getText().toString();
        if(!value1.equals("") && !value2.equals("")) {
            int v1 = Integer.parseInt(value1);
            int v2 = Integer.parseInt(value2);
            switch (item.getItemId()) {
                case R.id.btAdd1:
                    this.result.setText("" + (v1 + v2));
                    Log.i("res", "" + (v1+v2));
                    break;
                case R.id.btSub1:
                    this.result.setText("" + (v1-v2));
                    Log.i("res", "" + (v1-v2));
                    break;
                case R.id.btMulti1:
                    this.result.setText("" + (v1*v2));
                    Log.i("res", "" + (v1*v2));
                    break;
                case R.id.btDivide1:
                    this.result.setText("" + (v1/v2));
                    Log.i("res", "" + (v1/v2));
                    break;

            }
        }
        else {
            Log.e("EditText Field Cannot Be null", "Error");
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("SetTextI18n")
    public void doCalculate(View v) {
        String value1 = val1.getText().toString();
        String value2 = val2.getText().toString();
        if(!value1.equals("") && !value2.equals("")) {
            int v1 = Integer.parseInt(value1);
            int v2 = Integer.parseInt(value2);
            switch (v.getId()) {
                case R.id.btAdd:
                    this.result.setText("" + (v1 + v2));
                    Log.i("res", "" + (v1+v2));
                    break;
                case R.id.btSub:
                    this.result.setText("" + (v1-v2));
                    Log.i("res", "" + (v1-v2));
                    break;
                case R.id.btMulti:
                    this.result.setText("" + (v1*v2));
                    Log.i("res", "" + (v1*v2));
                    break;
                case R.id.btDivide:
                    this.result.setText("" + (v1/v2));
                    Log.i("res", "" + (v1/v2));
                    break;

            }
        }
        else {
            Log.e("EditText Field Cannot Be null", "Error");
        }
    }

}