package com.minimalist.weather.kotlin.di

import android.app.Application
import android.content.Context
import com.minimalist.weather.kotlin.feature.home.HomePagePresenter
import com.minimalist.weather.kotlin.model.data.RepositoryModule
import dagger.Binds
import dagger.Module

@Module
abstract class ApplicationModule {
    /**
     * 给APP需要Context的地方提供Context DI，本程序中[HomePagePresenter]和[RepositoryModule]需要Context注入
     */
    @Binds
    internal abstract fun bindContext(application: Application): Context
}
