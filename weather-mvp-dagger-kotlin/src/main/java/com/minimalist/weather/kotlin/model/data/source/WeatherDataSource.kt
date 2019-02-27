package com.minimalist.weather.kotlin.model.data.source

import com.minimalist.weather.kotlin.model.data.entity.weather.Weather

interface WeatherDataSource {

    interface LoadWeatherCallback {

        fun onWeatherLoaded(weather: Weather)

        fun onDataNotAvailable()
    }

    interface LoadWeathersCallback {

        fun onWeatherLoaded(weathers: List<Weather>)

        fun onDataNotAvailable()
    }

    /**
     * 查询已经保存的城市
     */
    fun queryAllSaveCity(callback: LoadWeathersCallback)

    /**
     * 查询制定城市天气信息
     */
    fun getWeather(cityId: String, callback: LoadWeatherCallback)

    /**
     * 根据City ID删除天气信息
     */
    fun deleteById(cityId: String)

    /**
     *插入或更新城市
     */
    fun insertWeather(weather: Weather)

    /**
     * 更新天气信息
     */
    fun updateWeather(weather: Weather)

    /**
     * 刷新天气信息
     */
    fun refreshWeathers()


}