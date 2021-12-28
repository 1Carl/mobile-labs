package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class FLayout extends AppCompatActivity {
    EditText et1, et2;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flayout);
        this.et1 = findViewById(R.id.et1);
        this.et2 = findViewById(R.id.et2);
        Intent i = getIntent();
        et1.setText(i.getStringExtra("et1"));
        et2.setText(i.getStringExtra("et2"));
    }

    public void buttonClicked(View view) {
        if(view.getId() == R.id.ok) {
            Intent i = new Intent();
            i.putExtra("et1", et1.getText().toString());
            i.putExtra("et2", et2.getText().toString());
            setResult(RESULT_OK, i);
        }
        else {
            setResult(RESULT_CANCELED);
        }
        finish();
    }
}
