package com.minimalist.weather.kotlin.di


import android.content.Context
import com.minimalist.weather.kotlin.main.WeatherApplication
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(ApplicationModule::class))
interface ApplicationComponent {

    val application: WeatherApplication

    val context: Context
}
