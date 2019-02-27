package com.minimalist.weather.kotlin.view.home

import com.minimalist.weather.kotlin.di.ActivityScoped
import com.minimalist.weather.kotlin.di.ApplicationComponent

import dagger.Component

@ActivityScoped
@Component(modules = arrayOf(HomePageModule::class), dependencies = arrayOf(ApplicationComponent::class))
interface HomePageComponent {

    fun inject(mainActivity: MainActivity)
}
