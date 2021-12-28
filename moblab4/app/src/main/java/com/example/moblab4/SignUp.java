package com.example.moblab4;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.moblab4.model.User;
import com.example.moblab4.model.UserDbHelper;

public class SignUp extends AppCompatActivity {
    EditText uname, pass, repass;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        uname = findViewById(R.id.uname);
        pass = findViewById(R.id.pass);
        repass = findViewById(R.id.repass);
    }

    public void btnclick(View v) {
        switch (v.getId()) {
            case R.id.ok:
                String unam = uname.getText().toString();
                String np = pass.getText().toString();
                String rp = repass.getText().toString();
                if(np.equals(rp)) {
                    if(UserDbHelper.usernameAvailable(unam, getBaseContext())) {
                        if(UserDbHelper.postUser(unam, np, getBaseContext())) {
                            Toast.makeText(getApplicationContext(),"Signed up successfully", Toast.LENGTH_SHORT).show();
                            finish();
                            return;
                        }
                        else {
                            Toast.makeText(getApplicationContext(),"Unexpected error occured", Toast.LENGTH_SHORT).show();
                        }
                    }
                    Toast.makeText(getApplicationContext(),"Username is already taken", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    Toast.makeText(getApplicationContext(),"Password doesn't match", Toast.LENGTH_SHORT).show();
                    return;
                }
            case R.id.cancel:
                finish();
        }
    }
}
