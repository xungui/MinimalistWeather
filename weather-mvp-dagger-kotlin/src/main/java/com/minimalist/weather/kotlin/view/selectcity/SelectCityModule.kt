package com.minimalist.weather.kotlin.view.selectcity

import com.minimalist.weather.kotlin.di.ActivityScoped
import com.minimalist.weather.kotlin.view.contract.SelectCityContract

import dagger.Module
import dagger.Provides

@Module
class SelectCityModule(private val view: SelectCityContract.View) {

    @Provides
    @ActivityScoped
    internal fun provideSelectCityContractView(): SelectCityContract.View {
        return view
    }
}
