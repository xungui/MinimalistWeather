package com.minimalist.weather.kotlin.di

import android.app.Application
import com.minimalist.weather.kotlin.main.WeatherApp
import com.minimalist.weather.kotlin.model.data.RepositoryModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(RepositoryModule::class, ApplicationModule::class, ActivityBindingModule::class, AndroidSupportInjectionModule::class))
interface AppComponent : AndroidInjector<WeatherApp> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): AppComponent.Builder

        fun build(): AppComponent
    }
}
