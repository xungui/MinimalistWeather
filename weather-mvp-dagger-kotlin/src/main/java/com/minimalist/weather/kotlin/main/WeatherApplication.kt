package com.minimalist.weather.kotlin.main

import android.app.Application
import android.content.Context
import android.util.Log
import com.minimalist.weather.kotlin.BuildConfig
import com.minimalist.weather.kotlin.di.ApplicationComponent
import com.minimalist.weather.kotlin.di.ApplicationModule
import com.minimalist.weather.kotlin.di.DaggerApplicationComponent

class WeatherApplication : Application() {
    var applicationComponent: ApplicationComponent? = null
        private set

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        Log.d(TAG, "attachBaseContext")
    }

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "onCreate start")
        instance = this
//        if (BuildConfig.DEBUG) {
//            StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build())
//            StrictMode.setVmPolicy(StrictMode.VmPolicy.Builder().detectAll().penaltyLog().build())
//        }
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this))
                .build()

        //初始化Stetho
        BuildConfig.STETHO.init(applicationContext)
        Log.d(TAG, "onCreate end")
    }

    companion object {
        private val TAG = "WeatherApp"
        lateinit var instance: WeatherApplication
            private set
    }
}
