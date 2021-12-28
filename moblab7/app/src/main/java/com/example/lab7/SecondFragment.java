package com.example.lab7;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SecondFragment extends Fragment {
    TextView lName;
    TextView fName;
    TextView gender;
    TextView jobtv;
    TextView degreetv;
    TextView classtv;
    Button button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_second,container,false);

        String ovog = getArguments().getString("ovog");
        String ner = getArguments().getString("ner");
        String huis = getArguments().getString("huis");
        String mergejil = getArguments().getString("mergejil");

        button = view.findViewById(R.id.buttonBagsh);

        lName = view.findViewById(R.id.textOvog);
        fName = view.findViewById(R.id.textNer);
        gender = view.findViewById(R.id.textHuis);
        jobtv = view.findViewById(R.id.textMergejil);

        lName.setText(ovog);
        fName.setText(ner);
        gender.setText(huis);
        jobtv.setText(mergejil);

        button.setOnClickListener(v -> {
            EditText tsol = view.findViewById(R.id.zeregtsol);
            EditText hicheel = view.findViewById(R.id.zaadaghicheel);

            degreetv = view.findViewById(R.id.textZereg);
            classtv = view.findViewById(R.id.textHicheel);

            degreetv.setText(tsol.getText().toString());
            classtv.setText(hicheel.getText().toString());
        });

        return view;
    }
}