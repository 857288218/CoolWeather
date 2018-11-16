package com.example.rjq.coolweather.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

import com.example.rjq.coolweather.R;
import com.example.rjq.coolweather.fragment.ChooseAreaFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if (prefs.getString("weather", null) != null) {
            Intent intent = new Intent(this, WeatherActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        ChooseAreaFragment chooseAreaFragment = (ChooseAreaFragment) getSupportFragmentManager().findFragmentById(R.id.choose_area_fragment);
        if (chooseAreaFragment.currrentLevel == ChooseAreaFragment.LEVEL_PROVINCE) {
            super.onBackPressed();
        } else {
            chooseAreaFragment.backLevel();
        }
    }

}
