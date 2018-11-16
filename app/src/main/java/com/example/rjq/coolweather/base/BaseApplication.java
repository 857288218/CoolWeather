package com.example.rjq.coolweather.base;

import android.app.Application;
import android.content.Context;

import org.litepal.LitePal;
import org.litepal.LitePalApplication;

public class BaseApplication extends Application {
    private static Application application;
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        LitePal.initialize(this);
        BaseApplication.application = this;
        BaseApplication.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return context;
    }
}
