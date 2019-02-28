package com.minimalist.weather.kotlin.view.home


import com.minimalist.weather.kotlin.di.ActivityScoped
import com.minimalist.weather.kotlin.di.FragmentScoped
import com.minimalist.weather.kotlin.view.contract.HomePageContract
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * This is a Dagger module. We use this to pass in the View dependency to the
 * [HomePagePresenter]
 */
@Module
abstract class HomePageModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract fun homePageFragment(): HomePageFragment

    @ActivityScoped
    @Binds
    abstract fun homePagePresenter(presenter: HomePagePresenter): HomePageContract.Presenter

}
