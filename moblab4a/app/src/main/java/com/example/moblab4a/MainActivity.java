package com.example.moblab4a;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.moblab4a.model.Translation;
import com.example.moblab4a.model.TranslationDbHelper;

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
        i.putExtra("mode", mode);
        i.setClass(getBaseContext(), ChangeMode.class);
        sp = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        startActivityForResult(i, 3);
        return super.onOptionsItemSelected(item);
    }

    private void getAllTranslations() {
        t.clear();
        t.addAll(TranslationDbHelper.getAllTranslations(getBaseContext()));
        setTranslation(0);
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
                TranslationDbHelper.deleteTranslation(t.get(curIndex)._id, getBaseContext());
                getAllTranslations();
                setTranslation(0);
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

    @SuppressLint("ApplySharedPref")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 3) {
            mode = data.getIntExtra("mode", 3);
            setMode();
        }
        getMode();
        clear();
        getAllTranslations();
    }
}