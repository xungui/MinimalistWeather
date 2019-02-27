package com.minimalist.weather.kotlin.view.selectcity


import com.minimalist.weather.kotlin.di.ActivityScoped
import com.minimalist.weather.kotlin.di.ApplicationComponent

import dagger.Component

@ActivityScoped
@Component(modules = arrayOf(SelectCityModule::class), dependencies = arrayOf(ApplicationComponent::class))
interface SelectCityComponent {

    fun inject(selectCityActivity: SelectCityActivity)
}
