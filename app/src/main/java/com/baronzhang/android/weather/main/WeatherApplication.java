package com.baronzhang.android.weather.main;

import android.app.Application;
import android.content.Context;
import android.os.StrictMode;
import android.util.Log;

import com.baronzhang.android.weather.BuildConfig;
import com.baronzhang.android.weather.di.ApplicationComponent;
import com.baronzhang.android.weather.di.ApplicationModule;
import com.baronzhang.android.weather.di.DaggerApplicationComponent;

/**
 * @author baronzhang (baron[dot]zhanglei[at]gmail[dot]com)
 * 16/2/4
 */
public class WeatherApplication extends Application {
    private static final String TAG = "WeatherApp";
    private ApplicationComponent applicationComponent;
    private static WeatherApplication application;

    public static WeatherApplication getInstance() {
        return application;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        Log.d(TAG, "attachBaseContext");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate start");
        application = this;
        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().penaltyLog().build());
        }
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();

        //初始化Stetho
        BuildConfig.STETHO.init(getApplicationContext());
        Log.d(TAG, "onCreate end");
    }


    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}
