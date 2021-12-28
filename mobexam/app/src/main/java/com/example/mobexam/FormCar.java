package com.example.mobexam;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mobexam.dbhelpers.CarDBHelper;
import com.example.mobexam.dbhelpers.OwnerDBHelper;
import com.example.mobexam.model.Owner;

import java.util.ArrayList;

public class FormCar extends AppCompatActivity {
    EditText uniquenumber, mark, motor;
    Spinner owner;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.car_form);

        ArrayList<Owner> owners = OwnerDBHelper.getowners(this);

        System.out.println(owners);
        uniquenumber = findViewById(R.id.uniquenumber);
        mark = findViewById(R.id.mark);
        motor = findViewById(R.id.motor);
        owner = findViewById(R.id.owner);
        ArrayAdapter aa = new ArrayAdapter<Owner>(this, R.layout.support_simple_spinner_dropdown_item, owners) {
        };

        owner.setAdapter(aa);

    }

    @SuppressLint("NonConstantResourceId")
    public void btnclicked(View view) {
        switch (view.getId()) {
            case R.id.ok:
                boolean success = CarDBHelper.postCar(
                        mark.getText().toString(),
                        Integer.parseInt(motor.getText().toString()),
                        uniquenumber.getText().toString(),
                        ((Owner) owner.getSelectedItem()).get_id(),
                        this
                );
                if(success) {
                    finish();
                    Toast.makeText(this, "Машин бүртгэгдлээ", Toast.LENGTH_LONG).show();
                }

                else {
                    Toast.makeText(this, "Улсын дугаарын алдаа", Toast.LENGTH_LONG).show();
                }

            case R.id.cancel:
                finish();
        }
    }
}
