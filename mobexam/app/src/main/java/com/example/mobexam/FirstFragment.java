package com.example.mobexam;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.mobexam.databinding.FragmentFirstBinding;
import com.example.mobexam.dbhelpers.CarDBHelper;
import com.example.mobexam.model.Car;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;
    TextView mark, motorvolume, uniquenumber, owner;
    Car car;
    Delete d;
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        car = getArguments().getParcelable("data");

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void setD(Delete d) {
        this.d = d;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        mark = getView().findViewById(R.id.mark);
        motorvolume = getView().findViewById(R.id.motorvolume);
        uniquenumber = getView().findViewById(R.id.uniquenumber);
        owner = getView().findViewById(R.id.owner);
        mark.setText(car.getMark());
        motorvolume.setText("" + car.getMotorvolume());
        uniquenumber.setText(car.getUniquenumber());
        owner.setText(car.getOwner().toString());
        getView().findViewById(R.id.btn).setOnClickListener(v -> {

            CarDBHelper.deleteCar(car.get_id(), getContext());
            d.deleted();
        });
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}