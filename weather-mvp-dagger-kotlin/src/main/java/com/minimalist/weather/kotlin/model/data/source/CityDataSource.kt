package com.minimalist.weather.kotlin.model.data.source

import com.minimalist.weather.kotlin.model.data.entity.city.City

interface CityDataSource {
    interface LoadCitiesCallback {

        fun onCitiesLoaded(cities: List<City>)

        fun onDataNotAvailable()
    }

    fun getCities(callback: LoadCitiesCallback)
}