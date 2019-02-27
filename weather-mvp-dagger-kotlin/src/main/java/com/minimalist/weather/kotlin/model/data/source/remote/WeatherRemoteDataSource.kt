package com.minimalist.weather.kotlin.model.data.source.remote

import android.support.annotation.VisibleForTesting
import com.minimalist.weather.kotlin.model.data.entity.CloudWeatherAdapter
import com.minimalist.weather.kotlin.model.data.entity.weather.Weather
import com.minimalist.weather.kotlin.model.data.source.WeatherDataSource
import com.minimalist.weather.kotlin.model.data.source.local.WeatherDao
import com.minimalist.weather.kotlin.model.http.ApiClient
import com.minimalist.weather.kotlin.utils.AppExecutors

class WeatherRemoteDataSource(val appExecutors: AppExecutors,
                              val weatherDao: WeatherDao) : WeatherDataSource {

    override fun refreshWeathers() {
        //do nothing
    }

    override fun queryAllSaveCity(callback: WeatherDataSource.LoadWeathersCallback) {
        //do nothing
    }

    override fun getWeather(cityId: String, callback: WeatherDataSource.LoadWeatherCallback) {
        appExecutors.networkIO.execute {
            val service = ApiClient.service
            try {
                //从网络获取天气数据
                val weatherLive = service.getWeatherLive(cityId).execute().body()
                val weatherForecast = service.getWeatherForecast(cityId).execute().body()
                val airLive = service.getAirLive(cityId).execute().body()
                val weather = CloudWeatherAdapter(weatherLive, weatherForecast, airLive).getWeather()
                appExecutors.mainThread.execute { callback.onWeatherLoaded(weather) }
            } catch (e: Exception) {
                appExecutors.mainThread.execute { callback.onDataNotAvailable()}
            }
        }
    }

    override fun deleteById(cityId: String) {
        //do nothing
    }

    override fun insertWeather(weather: Weather) {
        //do nothing
    }

    override fun updateWeather(weather: Weather) {
        //do nothing
    }

    companion object {
        private var INSTANCE: WeatherRemoteDataSource? = null

        @JvmStatic
        fun getInstance(appExecutors: AppExecutors, weatherDao: WeatherDao): WeatherRemoteDataSource {
            if (INSTANCE == null) {
                synchronized(WeatherRemoteDataSource::javaClass) {
                    INSTANCE = WeatherRemoteDataSource(appExecutors, weatherDao)
                }
            }
            return INSTANCE!!
        }

        @VisibleForTesting
        fun clearInstance() {
            INSTANCE = null
        }
    }
}