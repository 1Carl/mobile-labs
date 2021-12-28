package com.example.moblab5;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.moblab5.model.Translation;
import com.example.moblab5.model.TranslationDbHelper;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    int mode;
    TextView mongolian, foreign;
    int curIndex = 0;
    SharedPreferences sp;
    ArrayList<Translation> t = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getMode();
        mongolian = findViewById(R.id.mongolian);
        foreign = findViewById(R.id.foreign);
        getAllTranslations();
        View.OnLongClickListener lcl = v -> {
            Intent i = new Intent();
            i.setClass(getBaseContext(), Translate.class);
            i.putExtra("id", t.get(curIndex)._id);
            i.putExtra("translation", t.get(curIndex));
            startActivityForResult(i, 1);
            return false;
        };
        mongolian.setOnLongClickListener(lcl);
        foreign.setOnLongClickListener(lcl);
    }

    public boolean onLongClick(View v) {
        Intent i = new Intent();
        i.putExtra("id", t.get(curIndex)._id);
        i.putExtra("translation", t.get(curIndex));
        startActivityForResult(i, 1);
        return false;
    }

    private void getMode() {
        sp = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        mode = sp.getInt("mode", 3);
    }

    @SuppressLint("ApplySharedPref")
    private void setMode() {
        sp = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = sp.edit();
        ed.putInt("mode", mode);
        ed.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent i = new Intent();
        switch (item.getItemId()) {
            case R.id.changemode:
                i.putExtra("mode", mode);
                i.setClass(getBaseContext(), ChangeMode.class);
                sp = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
                startActivityForResult(i, 3);
                break;
            case R.id.csvfile:
                i.setType("*/*");
                i.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(i, 4);

        }
        return super.onOptionsItemSelected(item);
    }

    private void getAllTranslations() {
        t.clear();
        t.addAll(TranslationDbHelper.getAllTranslations(getBaseContext()));
        if(t.size() == 0) {
            mongolian.setText("");
            foreign.setText("");
            findViewById(R.id.edit).setEnabled(false);
            findViewById(R.id.previous).setEnabled(false);
            findViewById(R.id.next).setEnabled(false);
            findViewById(R.id.delete).setEnabled(false);
        } else {
            findViewById(R.id.edit).setEnabled(true);
            findViewById(R.id.previous).setEnabled(true);
            findViewById(R.id.next).setEnabled(true);
            findViewById(R.id.delete).setEnabled(true);
            setTranslation(0);
        }
    }

    private void setTranslation(int inc) {
        curIndex += inc;
        if(t.size() <= curIndex || curIndex < 0) {
            curIndex -= inc;
            return;
        }
        Translation tr = t.get(curIndex);
        if(mode != 1) {
            mongolian.setText(tr.mongolian);
        }
        if(mode != 2) {
            foreign.setText(tr.foreign);
        }
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setMessage("Are you sure with delete this translation")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        TranslationDbHelper.deleteTranslation(t.get(curIndex)._id, getBaseContext());
                        getAllTranslations();
                        setTranslation(-1);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        return b.create();
    }

    private void clear() {
        mongolian.setText("");
        foreign.setText("");
    }

    @SuppressLint("NonConstantResourceId")
    public void btnclicked(View v) {
        Intent i = new Intent();
        i.setClass(getBaseContext(), Translate.class);
        switch (v.getId()) {
            case R.id.previous:
                setTranslation(-1);
                return;
            case R.id.next:
                setTranslation(1);
                return;
            case R.id.delete:
                showDialog(3);
                return;
            case R.id.edit:
                i.putExtra("id", t.get(curIndex)._id);
                i.putExtra("translation", t.get(curIndex));
                startActivityForResult(i, 1);
                break;
            case R.id.plus:
                startActivityForResult(i, -1);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getAllTranslations();
    }

    @SuppressLint("ApplySharedPref")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 3) {
            mode = data.getIntExtra("mode", 3);
            setMode();
        }else if(requestCode == 4) {
            Uri url = data.getData();
            BufferedReader reader;
            InputStream is = null;
            String line;
            try {
                is = getContentResolver().openInputStream(url);
                reader = new BufferedReader(
                        new InputStreamReader(is, Charset.forName("UTF-8"))
                );
                reader.readLine();
                while((line = reader.readLine()) != null){
                    String[] ugs = line.split(",");
                    TranslationDbHelper.postTranslation(ugs[1].trim(), ugs[0].trim(), this);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        getMode();
        clear();
        getAllTranslations();
    }
}