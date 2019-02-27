package com.minimalist.weather.kotlin.model.data.source

class CityDataRepository private constructor( val cityDataLocalSource: CityDataSource) : CityDataSource {

    override fun getCities(callback: CityDataSource.LoadCitiesCallback) {
        cityDataLocalSource.getCities(callback)
    }

    companion object {
        private var INSTANCE: CityDataRepository? = null
        private val lock = Any()

        fun getInstance(cityDataLocalSource: CityDataSource): CityDataRepository {
            synchronized(lock) {
                if (INSTANCE == null) {
                    INSTANCE = CityDataRepository(cityDataLocalSource)
                }
                return INSTANCE!!
            }
        }
    }
}