package com.minimalist.weather.kotlin.di


import com.minimalist.weather.kotlin.view.home.HomePagePresenter
import com.minimalist.weather.kotlin.view.home.drawer.DrawerMenuPresenter
import com.minimalist.weather.kotlin.view.selectcity.SelectCityPresenter
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(ApplicationModule::class))
interface PresenterComponent {

    fun inject(presenter: HomePagePresenter)

    fun inject(presenter: SelectCityPresenter)

    fun inject(presenter: DrawerMenuPresenter)
}
 