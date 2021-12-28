package com.example.moblab4;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.moblab4.model.User;
import com.example.moblab4.model.UserDbHelper;

public class MainActivity extends AppCompatActivity {
    public final int LOGIN_REQUEST_CODE = 5;
    private String LAST_LOGGED_USERNAME = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void btnclick(View v){
        String uname = ((EditText) findViewById(R.id.uname)).getText().toString();
        String pass = ((EditText) findViewById(R.id.pass)).getText().toString();
        Intent i = new Intent();
        switch (v.getId()) {
            case R.id.signup:
                i.setClass(v.getContext(), SignUp.class);
                startActivity(i);
                break;
            case R.id.login:
                try {
                    User u = UserDbHelper.getUser(uname, pass, v.getContext());
                    i.putExtra("user", u);
                    i.setClass(v.getContext(), UserInfo.class);
                    this.LAST_LOGGED_USERNAME = u.username;
                    startActivityForResult(i, LOGIN_REQUEST_CODE);
                } catch (Resources.NotFoundException nfe) {
                    Toast.makeText(getApplicationContext(),"Username or Password wrong", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case LOGIN_REQUEST_CODE:
                ((EditText) findViewById(R.id.uname)).setText(LAST_LOGGED_USERNAME);
            default:
                ;
        }
    }
}