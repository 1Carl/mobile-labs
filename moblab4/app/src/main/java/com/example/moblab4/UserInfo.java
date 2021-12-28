package com.example.moblab4;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.moblab4.model.User;
import com.example.moblab4.model.UserDbHelper;

import java.text.ParseException;
import java.util.Date;

public class UserInfo extends AppCompatActivity {
    private EditText age, gender, phoneNo;
    private User u;
    private TextView username;
    private DatePicker dob;
    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
    @SuppressLint("SetTextI18n")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userinfo);
        age = findViewById(R.id.age);
        gender = findViewById(R.id.gender);
        phoneNo = findViewById(R.id.phoneNo);
        username = findViewById(R.id.username);
        dob = findViewById(R.id.dob);
        Intent i = getIntent();
        u = i.getParcelableExtra("user");
        age.setText("" + u.age);
        gender.setText(u.gender);
        phoneNo.setText(u.phoneNo);
        username.setText(u.username);
        try {
            Date d = formatter.parse(u.dob);
            dob.updateDate(d.getYear(), d.getMonth(), d.getDate());
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(),"Failed to parse Date", Toast.LENGTH_SHORT).show();
        }
    }


    public void btnclick(View v) {
        switch (v.getId()) {
            case R.id.changeInfo:
                u.age = Integer.parseInt(age.getText().toString());
                u.gender = gender.getText().toString();
                u.phoneNo = phoneNo.getText().toString();
                u.username = username.getText().toString();
                Date d = new Date(dob.getYear(), dob.getMonth(), dob.getDayOfMonth());
                u.dob = formatter.format(d);
                if(UserDbHelper.putUser(u, getBaseContext())) {
                    Toast.makeText(getApplicationContext(),"Updated information successfully", Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(getApplicationContext(),"Failed to update information", Toast.LENGTH_SHORT).show();
                break;
            case R.id.changePass:
                Intent i = new Intent();
                i.putExtra("uname", u.username);
                i.setClass(getBaseContext(), ChangePassword.class);
                startActivity(i);
        }
    }
}