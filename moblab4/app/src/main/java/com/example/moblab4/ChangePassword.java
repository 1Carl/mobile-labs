package com.example.moblab4;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.moblab4.model.User;
import com.example.moblab4.model.UserDbHelper;

public class ChangePassword extends AppCompatActivity {
    EditText opass, npass, rpass;
    String username;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.changepassword);
        username = getIntent().getStringExtra("uname");
        opass = findViewById(R.id.pass);
        npass = findViewById(R.id.newpass);
        rpass = findViewById(R.id.repass);
    }

    public void btnclick(View v) {
        switch (v.getId()) {
            case R.id.ok:
                try {
                    User u = UserDbHelper.getUser(username, opass.getText().toString(), getBaseContext());
                    String np = npass.getText().toString();
                    String rp = rpass.getText().toString();
                    if(np.equals(rp)) {
                        u.password = np;
                        if(UserDbHelper.putUser(u, getBaseContext())) {
                            Toast.makeText(getApplicationContext(),"Password changed successfully", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(getApplicationContext(),"New password doesn't match", Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (Resources.NotFoundException e) {
                    Toast.makeText(getApplicationContext(),"Password is wrong", Toast.LENGTH_SHORT).show();
                }
                finish();
            case R.id.cancel:
                finish();
        }
    }
}
