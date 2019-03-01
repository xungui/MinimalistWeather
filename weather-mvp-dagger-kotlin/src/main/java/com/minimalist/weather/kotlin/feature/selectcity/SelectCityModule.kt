package com.minimalist.weather.kotlin.feature.selectcity

import com.minimalist.weather.kotlin.di.ActivityScoped
import com.minimalist.weather.kotlin.di.FragmentScoped
import com.minimalist.weather.kotlin.feature.contract.SelectCityContract
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class SelectCityModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract fun selectCityFragment(): SelectCityFragment

    @ActivityScoped
    @Binds
    abstract fun selectCityPresenter(presenter: SelectCityPresenter): SelectCityContract.Presenter
}
