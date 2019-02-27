package com.minimalist.weather.kotlin.model.data.source.local

import com.minimalist.weather.kotlin.model.data.source.CityDataSource
import com.minimalist.weather.kotlin.utils.AppExecutors

/**
 * 直接操作数据库获取数据，没有远程数据库获取数据问题
 */
class CityLocalDataSource private constructor(
        private val appExecutors: AppExecutors,
        private val cityDao: CityDao
) : CityDataSource {

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

    companion object {
        @Volatile
        private var INSTANCE: CityLocalDataSource? = null
        private val lock = Any()

        @JvmStatic
        fun getInstance(appExecutors: AppExecutors, cityDao: CityDao): CityLocalDataSource {
            if (INSTANCE == null) {
                synchronized(lock) {
                    if (INSTANCE == null) {
                        INSTANCE = CityLocalDataSource(appExecutors, cityDao)
                    }
                }
            }
            return INSTANCE!!
        }
    }
}