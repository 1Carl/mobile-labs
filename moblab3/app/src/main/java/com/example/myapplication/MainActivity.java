package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.time.LocalDateTime;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private Bundle ll, rl, tl, fl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Bundle b = null;
        String a = "android.intent.";
        switch (item.getItemId()) {
            case R.id.flayout:
                a+= "cfl";
                b = fl;
                break;
            case R.id.llayout:
                b = ll;
                a+= "cll";
                break;
            case R.id.tlayout:
                b = tl;
                a+= "ctl";
                break;
            case R.id.rlayout:
                b = rl;
                a+= "crl";
                break;
        }
        Intent i = new Intent();
        i.setAction(a);
        if(b != null) {
            i.putExtras(b);
        }
        startActivity(i);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) {
            switch (requestCode) {
                case 1:
                    this.fl = data.getExtras();
                    break;
                case 2:
                    this.ll = data.getExtras();
                    break;
                case 3:
                    this.tl = data.getExtras();
                    break;
                case 4:
                    this.rl = data.getExtras();
            }

        }
    }

    @SuppressLint("SetTextI18n")
    public void buttonClicked(View v) {
        Class c = null;
        Bundle b = null;
        int code = 0;
        switch (v.getId()) {
            case R.id.flayout:
                c = FLayout.class;
                b = fl;
                code = 1;
                break;
            case R.id.llayout:
                c = LLayout.class;
                b = ll;
                code = 2;
                break;
            case R.id.tlayout:
                c = TLayout.class;
                b = tl;
                code = 3;
                break;
            case R.id.rlayout:
                c = RLayout.class;
                b = rl;
                code = 4;
                break;
            case R.id.call:
                Intent i = new Intent(Intent.ACTION_DIAL);
                i.setData(Uri.parse("tel:86709400"));
                if (i.resolveActivity(getPackageManager()) != null) {
                    startActivity(i);
                }
                return;
            case R.id.image:
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                try {
                    startActivityForResult(takePictureIntent, 1);
                } catch (ActivityNotFoundException e) {
                    // display error state to the user
                }
                return;

            case R.id.video:
                String q = "find something";
                Intent intent = new Intent(Intent.ACTION_WEB_SEARCH );
                intent.putExtra(SearchManager.QUERY, q);
                startActivity(intent);
                return;
        }
        Intent i = new Intent(this, c);
        if(b != null) {
            i.putExtras(b);
        }
        startActivityForResult(i, code);
    }

}