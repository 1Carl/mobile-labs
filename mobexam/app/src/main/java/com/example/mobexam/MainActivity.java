package com.example.mobexam;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.mobexam.dbhelpers.CarDBHelper;
import com.example.mobexam.model.Car;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements Delete {
    FragmentManager fm;
    ListView lv;
    ArrayList<Car> cars;
    ArrayAdapter ad;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.fm = getSupportFragmentManager();
        lv = findViewById(R.id.carsList);
        updateAll();
    }

    void setFragment(Car car) {
        Bundle b = new Bundle();
        b.putParcelable("data", car);
        FirstFragment fragment = new FirstFragment();
        fragment.setD(this);
        fragment.setArguments(b);
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.secondFragment, fragment);
        ft.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent i = new Intent();
        switch (item.getItemId()) {
            case R.id.postcar:
                i.setClass(this, FormCar.class);
                break;
            case R.id.postowner:
                i.setClass(this, FormOwner.class);
                break;
        }
        startActivity(i);
        return super.onOptionsItemSelected(item);
    }

    void updateAll () {
        cars = CarDBHelper.getcars(this);
        ad = new ArrayAdapter<>(this, R.layout.list_item, cars);
        lv.setAdapter(ad);
        lv.setOnItemClickListener((parent, view, position, id) -> {
            setFragment(cars.get(position));
        });
    }

    @Override
    protected void onResume() {
        updateAll();
        super.onResume();

    }

    @Override
    public void deleted() {
        updateAll();
    }
}