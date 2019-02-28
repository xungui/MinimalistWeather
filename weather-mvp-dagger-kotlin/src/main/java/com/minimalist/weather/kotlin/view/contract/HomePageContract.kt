package com.minimalist.weather.kotlin.view.contract


import com.minimalist.weather.kotlin.model.data.entity.weather.Weather

interface HomePageContract {

    interface View : BaseView {

        fun displayWeatherInformation(weather: Weather)
    }

    interface Presenter : BasePresenter<View> {

        fun loadWeather(cityId: String, refreshNow: Boolean)
    }
}
