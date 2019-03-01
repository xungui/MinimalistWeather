package com.minimalist.weather.kotlin.model.data.source.local

import com.minimalist.weather.kotlin.model.data.entity.weather.Weather
import com.minimalist.weather.kotlin.model.data.source.WeatherDataSource
import com.minimalist.weather.kotlin.utils.AppExecutors
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 直接操作数据库获取数据
 *
 * 在数据没有Dirty时，先访问内存有没有数据，再访问数据库有没有数据，之后再去网络获取数据
 */
@Singleton
class WeatherLocalDataSource @Inject constructor(
        private val appExecutors: AppExecutors, private val weatherDao: WeatherDao) : WeatherDataSource {

    override fun queryAllSaveCity(callback: WeatherDataSource.LoadWeathersCallback) {
        appExecutors.diskIO.execute {
            val weathers = weatherDao.queryAllSaveCity()
            if (weathers.isEmpty()) {
                appExecutors.mainThread.execute { callback.onDataNotAvailable() }
            }
            for (weather in weathers) {
                val cityId = weather.cityId
                weather.airQualityLive = weatherDao.queryAirQuality(cityId)
                weather.weatherForecasts = weatherDao.queryWeatherForecast(cityId)
                weather.lifeIndexes = weatherDao.queryLifeIndex(cityId)
                weather.weatherLive = weatherDao.queryWeatherLive(cityId)
            }
            appExecutors.mainThread.execute { callback.onWeatherLoaded(weathers) }
        }
    }

    override fun getWeather(cityId: String, forceRefresh: Boolean, callback: WeatherDataSource.LoadWeatherCallback) {
        appExecutors.diskIO.execute {
            val weather = weatherDao.queryWeather(cityId)
            appExecutors.mainThread.execute {
                if (weather != null) {
                    callback.onWeatherLoaded(weather)
                } else {
                    callback.onDataNotAvailable()
                }
            }
        }
    }

    override fun deleteById(cityId: String) {
        appExecutors.diskIO.execute {
            weatherDao.deleteWeather(cityId)
            weatherDao.deleteAirQuality(cityId)
            weatherDao.deleteLifeIndex(cityId)
            weatherDao.deleteWeatherLive(cityId)
            weatherDao.deleteWeatherForecast(cityId)
        }
    }

    override fun insertWeather(weather: Weather) {
        appExecutors.diskIO.execute {
            weatherDao.insertWeather(weather)
            weather.airQualityLive?.let { weatherDao.insertAirQuality(it) }
            weather.weatherLive?.let { weatherDao.insertWeatherLive(it) }
            val lifeIndexs = weather.lifeIndexes
            if (lifeIndexs != null) {
                for (lifeIndex in lifeIndexs) {
                    weatherDao.insertLifeIndex(lifeIndex)
                }
            }

            val weatherForecasts = weather.weatherForecasts
            if (weatherForecasts != null) {
                for (weatherForecast in weatherForecasts) {
                    weatherDao.insertWeatherForecast(weatherForecast)
                }
            }
        }
    }

    override fun updateWeather(weather: Weather) {
        appExecutors.diskIO.execute {
            weatherDao.updateWeather(weather)
            weather.airQualityLive?.let { weatherDao.updateAirQuality(it) }
            weather.weatherLive?.let { weatherDao.updateWeatherLive(it) }
            val lifeIndexs = weather.lifeIndexes
            if (lifeIndexs != null) {
                for (lifeIndex in lifeIndexs) {
                    weatherDao.updateLifeIndex(lifeIndex)
                }
            }

            val weatherForecasts = weather.weatherForecasts
            if (weatherForecasts != null) {
                for (weatherForecast in weatherForecasts) {
                    weatherDao.updateWeatherForecast(weatherForecast)
                }
            }
        }
    }
}