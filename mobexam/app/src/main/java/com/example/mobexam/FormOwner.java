package com.example.mobexam;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mobexam.dbhelpers.OwnerDBHelper;

public class FormOwner extends AppCompatActivity {
    EditText fname, lname, rnumber;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.owner_form);
        fname = findViewById(R.id.fname);
        lname = findViewById(R.id.lname);
        rnumber = findViewById(R.id.rnumber);
    }


    @SuppressLint("NonConstantResourceId")
    public void btnclicked(View view) {
        switch (view.getId()) {
            case R.id.ok:
                boolean success = OwnerDBHelper.postOwner(
                        fname.getText().toString(),
                        lname.getText().toString(),
                        rnumber.getText().toString(),
                        this
                );
                if(success) {
                    Toast.makeText(this, "Registered Successfully", Toast.LENGTH_LONG).show();
                    finish();
                }

                else {
                    Toast.makeText(this, "Register error", Toast.LENGTH_LONG).show();
                }

            case R.id.cancel:
                finish();
        }
    }
}
