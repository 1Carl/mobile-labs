package com.example.moblab5;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.moblab5.R;
import com.example.moblab5.model.Translation;
import com.example.moblab5.model.TranslationDbHelper;

public class Translate extends AppCompatActivity {
    EditText mongolian, foreign;
    int id;
    Translation t;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.translate);
        mongolian = findViewById(R.id.mongolian);
        foreign = findViewById(R.id.foreign);
        Intent i = getIntent();
        id = i.getIntExtra("id", -1);
        t = i.getParcelableExtra("translation");
        if(id != -1) {
            mongolian.setText(t.mongolian);
            foreign.setText(t.foreign);
        }

    }

    public void btnclicked(View view) {
        Intent i = new Intent();
        switch (view.getId()) {
            case R.id.ok:
                if(id == -1) {
                    long id = TranslationDbHelper.postTranslation(mongolian.getText().toString(), foreign.getText().toString(), getBaseContext());
                    Translation t = new Translation((int) id, mongolian.getText().toString(), foreign.getText().toString());
                }
                else {
                    t.mongolian = mongolian.getText().toString();
                    t.foreign = foreign.getText().toString();
                    TranslationDbHelper.putTranslation(t, getBaseContext());
                    i.putExtra("translation", t);
                }
                setResult(1, i);
                finish();
                break;
            case R.id.cancel:
                setResult(0, i);
                finish();
        }
    }
}
