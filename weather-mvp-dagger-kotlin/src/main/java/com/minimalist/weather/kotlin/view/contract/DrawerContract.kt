package com.minimalist.weather.kotlin.view.contract

import com.minimalist.weather.kotlin.model.data.entity.weather.Weather
import java.io.InvalidClassException

interface DrawerContract {

    interface View : BaseView {

        fun displaySavedCities(weatherList: List<Weather>)
    }

    interface Presenter : BasePresenter<View> {

        fun loadSavedCities()

        fun deleteCity(cityId: String)

        @Throws(InvalidClassException::class)
        fun saveCurrentCityToPreference(cityId: String)
    }
}
