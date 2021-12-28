package com.example.lab7;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {

    FragmentTransaction ft;
    FrameLayout sv;
    String gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sv = findViewById(R.id.Fragment2);
        sv.setVisibility(View.GONE);

        Spinner huis = findViewById(R.id.huis);
        ArrayAdapter<CharSequence> huisadapter = ArrayAdapter.createFromResource(this, R.array.huis_array, android.R.layout.simple_spinner_item);
        huisadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        huis.setAdapter(huisadapter);
        huis.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String text = parent.getItemAtPosition(position).toString();
                gender = text;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Spinner jobSpinner = findViewById(R.id.mergejil);
        ArrayAdapter<CharSequence> jobadapter = ArrayAdapter.createFromResource(this, R.array.mergejil_array, android.R.layout.simple_spinner_item);
        jobadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        jobSpinner.setAdapter(jobadapter);
        jobSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String text = parent.getItemAtPosition(position).toString();
                ft = getSupportFragmentManager().beginTransaction();

                EditText ovogInput = findViewById(R.id.LastName);
                EditText nerInput = findViewById(R.id.FirstName);

                Bundle bundle = new Bundle();
                bundle.putString("ovog", ovogInput.getText().toString());
                bundle.putString("ner", nerInput.getText().toString());
                bundle.putString("huis", gender);
                bundle.putString("mergejil", text);

                if(text.equals("Teacher")){
                    SecondFragment secondFragment = new SecondFragment();
                    secondFragment.setArguments(bundle);
                    ft.replace(R.id.Fragment2, secondFragment);
                    ft.commit();
                    sv.setVisibility(View.VISIBLE);
                }else if(text.equals("Student")) {
                    ThirdFragment thirdFragment = new ThirdFragment();
                    thirdFragment.setArguments(bundle);
                    ft.replace(R.id.Fragment2, thirdFragment);
                    ft.commit();
                    sv.setVisibility(View.VISIBLE);
                }else {
                    sv.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
}