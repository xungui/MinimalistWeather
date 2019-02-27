package com.minimalist.weather.kotlin.view.contract

import com.minimalist.weather.kotlin.model.data.entity.weather.Weather
import com.minimalist.weather.kotlin.view.home.drawer.DrawerMenuPresenter
import java.io.InvalidClassException

interface DrawerContract {

    interface View : BaseView<DrawerMenuPresenter> {

        fun displaySavedCities(weatherList: List<Weather>)
    }

    interface Presenter : BasePresenter {

        fun loadSavedCities()

        fun deleteCity(cityId: String)

        @Throws(InvalidClassException::class)
        fun saveCurrentCityToPreference(cityId: String)
    }
}
