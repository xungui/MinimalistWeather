package com.minimalist.weather.kotlin.model.data.source

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CityDataRepository @Inject constructor(private val cityDataLocalSource: CityDataSource)
    : CityDataSource {

    override fun getCities(callback: CityDataSource.LoadCitiesCallback) {
        cityDataLocalSource.getCities(callback)
    }
}