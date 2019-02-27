package com.minimalist.weather.kotlin.view.home.drawer

import com.minimalist.weather.kotlin.di.ActivityScoped
import com.minimalist.weather.kotlin.view.contract.DrawerContract

import dagger.Module
import dagger.Provides

@Module
class DrawerMenuModule(private val view: DrawerContract.View) {

    @Provides
    @ActivityScoped
    internal fun provideCityManagerContactView(): DrawerContract.View {
        return view
    }
}
