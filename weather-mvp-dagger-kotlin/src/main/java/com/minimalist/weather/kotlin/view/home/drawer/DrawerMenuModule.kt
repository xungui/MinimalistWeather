package com.minimalist.weather.kotlin.view.home.drawer

import com.minimalist.weather.kotlin.di.ActivityScoped
import com.minimalist.weather.kotlin.di.FragmentScoped
import com.minimalist.weather.kotlin.view.contract.DrawerContract
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class DrawerMenuModule {

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun drawerMenuFragment(): DrawerMenuFragment

    @ActivityScoped
    @Binds
    internal abstract fun drawerMenuPresenter(presenter: DrawerMenuPresenter): DrawerContract.Presenter
}
