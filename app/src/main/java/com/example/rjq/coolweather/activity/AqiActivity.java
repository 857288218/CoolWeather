package com.example.rjq.coolweather.activity;

import android.databinding.DataBindingUtil;
import android.support.constraint.ConstraintSet;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.TransitionManager;
import android.view.View;

import com.example.rjq.coolweather.R;
import com.example.rjq.coolweather.databinding.ActivityAqiBinding;
import com.example.rjq.coolweather.util.ThemeUtil;

import java.security.spec.RSAOtherPrimeInfo;

public class AqiActivity extends AppCompatActivity {

    private ConstraintSet applyConstraintSet = new ConstraintSet();
    private ActivityAqiBinding activityAqiBinding;
    private int aqi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ThemeUtil.setStatusBarColor(this, getResources().getColor(R.color.color_f2f6fa));
        activityAqiBinding = DataBindingUtil.setContentView(this, R.layout.activity_aqi);
        activityAqiBinding.setAqi(getIntent().getStringExtra("aqi"));
        activityAqiBinding.setPm(getIntent().getStringExtra("pm2.5"));
        aqi = Integer.parseInt(getIntent().getStringExtra("aqi"));
        activityAqiBinding.setQuality(aqi < 50 ? "优" : (aqi >= 50 && aqi < 100 ? "良" : (aqi >= 100 && aqi < 150 ? "轻度" : (aqi >= 150 && aqi < 200 ? "中度" : (aqi >= 200 && aqi < 300 ? "重度" : "严重")))));
        activityAqiBinding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        applyConstraintSet.clone(activityAqiBinding.llOrder);
        activityAqiBinding.llOrder.post(new Runnable() {
            @Override
            public void run() {
                TransitionManager.beginDelayedTransition(activityAqiBinding.llOrder);
                applyConstraintSet.setMargin(R.id.iv_delta,ConstraintSet.START,(int)(aqi/500.0 * activityAqiBinding.llOrder.getWidth() - (activityAqiBinding.ivDelta.getWidth() / 2)));
                applyConstraintSet.applyTo(activityAqiBinding.llOrder);
            }
        });

    }

}
