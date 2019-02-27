package com.minimalist.weather.kotlin.view.home


import com.minimalist.weather.kotlin.di.ActivityScoped
import com.minimalist.weather.kotlin.view.contract.HomePageContract

import dagger.Module
import dagger.Provides

/**
 * This is a Dagger module. We use this to pass in the View dependency to the
 * [HomePagePresenter]
 */
@Module
class HomePageModule(private val view: HomePageContract.View) {

    @Provides
    @ActivityScoped
    internal fun provideHomePageContractView(): HomePageContract.View {
        return view
    }

}
