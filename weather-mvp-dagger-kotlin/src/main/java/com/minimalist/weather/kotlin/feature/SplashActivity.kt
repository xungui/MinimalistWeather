package com.minimalist.weather.kotlin.feature


import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.minimalist.weather.kotlin.feature.home.MainActivity
import com.minimalist.weather.kotlin.model.data.source.local.CityDatabase
import com.minimalist.weather.kotlin.model.preference.PreferenceHelper
import com.minimalist.weather.kotlin.model.preference.WeatherSettings
import com.minimalist.weather.kotlin.utils.system.StatusBarHelper
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.io.InvalidClassException

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StatusBarHelper.statusBarLightMode(this)
        Observable.just<String>(initCityData())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { gotoMainPage() }
    }

    private fun gotoMainPage() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    /**
     * 初始化应用数据
     */
    private fun initCityData(): String? {
        PreferenceHelper.loadDefaults()
        if (PreferenceHelper.sharedPreferences.getBoolean(WeatherSettings.SETTINGS_FIRST_USE.id, false)) {
            try {
                PreferenceHelper.savePreference(WeatherSettings.SETTINGS_CURRENT_CITY_ID, "101020100")
                PreferenceHelper.savePreference(WeatherSettings.SETTINGS_FIRST_USE, false)
            } catch (e: InvalidClassException) {
                e.printStackTrace()
            }
        }
        CityDatabase.importCityDB()
        return null
    }
}
