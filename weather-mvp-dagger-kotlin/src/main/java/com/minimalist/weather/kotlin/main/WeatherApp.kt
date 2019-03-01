package com.minimalist.weather.kotlin.main

import android.os.StrictMode
import com.minimalist.weather.kotlin.BuildConfig
import com.minimalist.weather.kotlin.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class WeatherApp : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().application(this).build()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
//        setupStrictMode()
        setupStetho()
    }

    private fun setupStrictMode() {
        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .build())
            StrictMode.setVmPolicy(StrictMode.VmPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .build())
        }
    }

    /**
     *初始化Stetho
     */
    private fun setupStetho() {
        BuildConfig.STETHO.init(applicationContext)
    }

    companion object {
        private val TAG = "WeatherApp"
        lateinit var instance: WeatherApp
            private set
    }
}

