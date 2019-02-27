package com.minimalist.weather.kotlin.di


import android.content.Context
import com.minimalist.weather.kotlin.main.WeatherApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule(private val context: Context) {

    @Provides
    @Singleton
    internal fun provideApplication(): WeatherApplication {
        return context.applicationContext as WeatherApplication
    }

    @Provides
    @Singleton
    internal fun provideContext(): Context {
        return context
    }
}
