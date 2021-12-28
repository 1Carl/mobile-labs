package com.example.lab7;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ThirdFragment extends Fragment {
    TextView lName;
    TextView fName;
    TextView gender;
    TextView jobtv;
    TextView school;
    TextView major;
    TextView grade;
    TextView gpa;
    Button button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_third,container,false);

        String ovog = getArguments().getString("ovog");
        String ner = getArguments().getString("ner");
        String huis = getArguments().getString("huis");
        String mergejil = getArguments().getString("mergejil");

        button = view.findViewById(R.id.button);

        lName = view.findViewById(R.id.textOvog);
        fName = view.findViewById(R.id.textNer);
        gender = view.findViewById(R.id.textHuis);
        jobtv = view.findViewById(R.id.textMergejil);

        lName.setText(ovog);
        fName.setText(ner);
        gender.setText(huis);
        jobtv.setText(mergejil);

        button.setOnClickListener(v -> {
            EditText surgiili = view.findViewById(R.id.surguuli);
            EditText angi = view.findViewById(R.id.angi);
            EditText kurs = view.findViewById(R.id.kurs);
            EditText golch = view.findViewById(R.id.golch);

            school = view.findViewById(R.id.textSurguuli);
            major = view.findViewById(R.id.textAngi);
            grade = view.findViewById(R.id.textKurs);
            gpa = view.findViewById(R.id.textGolch);

            school.setText(surgiili.getText().toString());
            major.setText(angi.getText().toString());
            grade.setText(kurs.getText().toString());
            gpa.setText(golch.getText().toString());
        });
        return view;
    }
}