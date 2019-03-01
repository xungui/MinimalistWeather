package com.minimalist.weather.kotlin.feature.contract


import com.minimalist.weather.kotlin.model.data.entity.city.City

interface SelectCityContract {

    interface View : BaseView {

        fun displayCities(cities: List<City>)
    }

    interface Presenter : BasePresenter<View> {

        fun loadCities()
    }
}
