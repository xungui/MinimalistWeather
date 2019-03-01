package com.minimalist.weather.kotlin.model.data.source

import com.minimalist.weather.kotlin.model.data.Local
import com.minimalist.weather.kotlin.model.data.Remote
import com.minimalist.weather.kotlin.model.data.entity.weather.Weather
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

/**
 *
 * 在数据没有Dirty时，先访问内存有没有数据，再访问数据库有没有数据，之后再去网络获取数据
 *
 * 如果数据已经dirty，访问网络更新数据
 */
@Singleton
class WeatherDataRepository : WeatherDataSource {
    /**
     * This variable has public visibility so it can be accessed from tests.
     */
    var cachedWeathers: LinkedHashMap<String, Weather> = LinkedHashMap()

    private val weatherLocalDataSource: WeatherDataSource
    private val weatherRemoteDataSource: WeatherDataSource

    @Inject
    constructor(@Local weatherLocalDataSource: WeatherDataSource,
                @Remote weatherRemoteDataSource: WeatherDataSource) {
        this.weatherLocalDataSource = weatherLocalDataSource
        this.weatherRemoteDataSource = weatherRemoteDataSource
    }

    override fun queryAllSaveCity(callback: WeatherDataSource.LoadWeathersCallback) {
        weatherLocalDataSource.queryAllSaveCity(callback)
    }

    override fun getWeather(cityId: String, forceRefresh: Boolean, callback: WeatherDataSource.LoadWeatherCallback) {
        val weatherInCache = getWeatherWithId(cityId)
        //同一个小时内不会发布新的天气信息
        if (weatherInCache != null && isInFifteenMinute(weatherInCache) && !forceRefresh) {
            callback.onWeatherLoaded(weatherInCache)
        }
        if (weatherInCache == null || forceRefresh) {
            getTasksFromRemoteDataSource(cityId, callback)
        }
    }

    override fun deleteById(cityId: String) {
        weatherLocalDataSource.deleteById(cityId)
    }

    override fun insertWeather(weather: Weather) {
        weatherLocalDataSource.insertWeather(weather)
    }

    override fun updateWeather(weather: Weather) {
        weatherLocalDataSource.updateWeather(weather)
    }

    /**
     * 判断是否刷新的时间是否太近
     */
    private fun isInFifteenMinute(weather: Weather?): Boolean {
        weather?.weatherLive.let {
            val time = weather?.weatherLive?.time ?: return false
            val interval = System.currentTimeMillis() - time
            return interval > 60_000 * 15//15分钟刷新
        }
    }

    private fun getWeatherWithId(id: String) = cachedWeathers[id]

    private fun getTasksFromRemoteDataSource(cityId: String, callback: WeatherDataSource.LoadWeatherCallback) {
        weatherRemoteDataSource.getWeather(cityId, true, object : WeatherDataSource.LoadWeatherCallback {
            override fun onWeatherLoaded(weather: Weather) {
                insertWeather(weather)
                callback.onWeatherLoaded(weather)
            }

            override fun onDataNotAvailable() {
                callback.onDataNotAvailable()
            }
        })
    }
}