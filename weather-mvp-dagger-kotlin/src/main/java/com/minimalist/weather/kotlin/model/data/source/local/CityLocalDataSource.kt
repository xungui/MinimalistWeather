package com.minimalist.weather.kotlin.model.data.source.local

import com.minimalist.weather.kotlin.model.data.source.CityDataSource
import com.minimalist.weather.kotlin.utils.AppExecutors
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 直接操作数据库获取数据，没有远程数据库获取数据问题
 */
@Singleton
class CityLocalDataSource @Inject constructor(private val appExecutors: AppExecutors,
                                              private val cityDao: CityDao) : CityDataSource {

    override fun getCities(callback: CityDataSource.LoadCitiesCallback) {
        appExecutors.diskIO.execute {
            val cities = cityDao.queryAllSaveCity()
            appExecutors.mainThread.execute {
                if (cities.isEmpty()) {
                    callback.onDataNotAvailable()
                } else {
                    callback.onCitiesLoaded(cities)
                }
            }
        }
    }
}