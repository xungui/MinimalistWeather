package com.minimalist.weather.kotlin.model.data.source.local

import android.arch.persistence.room.*
import com.minimalist.weather.kotlin.model.data.entity.weather.*

@Dao
interface WeatherDao {

    /**
     *搜索指定ID的城市
     */
    @Query("SELECT * FROM Weather")
    fun queryAllSaveCity(): List<Weather>

    /**
     *搜索指定ID的城市
     */
    @Query("SELECT * FROM Weather WHERE cityId = :id")
    fun queryWeather(id: String): Weather?

    /**
     *插入或更新城市
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWeather(weather: Weather)

    /**
     * 更新天气信息
     */
    @Update
    fun updateWeather(weather: Weather)

    /**
     *删除天气信息
     */
    @Query("DELETE FROM Weather WHERE cityId = :id")
    fun deleteWeather(id: String)

    /**
     * 根据城市ID查询控制质量
     */
    @Query("SELECT * FROM AirQuality WHERE cityId = :id")
    fun queryAirQuality(id: String): AirQualityLive

    /**
     *插入控制质量信息
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAirQuality(weather: AirQualityLive)

    /**
     * 更新控制质量信息
     */
    @Update
    fun updateAirQuality(weather: AirQualityLive)

    /**
     *删除控制质量信息
     */
    @Query("DELETE FROM AirQuality WHERE cityId = :id")
    fun deleteAirQuality(id: String)


    @Query("SELECT * FROM LifeIndex WHERE cityId = :id")
    fun queryLifeIndex(id: String): List<LifeIndex>

    /**
     *插入或更新城市
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLifeIndex(lifeIndex: LifeIndex)

    /**
     * 更新天气信息
     */
    @Update
    fun updateLifeIndex(lifeIndex: LifeIndex)

    /**
     *删除天气信息
     */
    @Query("DELETE FROM LifeIndex WHERE cityId = :id")
    fun deleteLifeIndex(id: String)

    /**
     *查询天气预报
     */
    @Query("SELECT * FROM WeatherForecast WHERE cityId = :id")
    fun queryWeatherForecast(id: String): List<WeatherForecast>

    /**
     *插入或更新城市
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWeatherForecast(weatherForecast: WeatherForecast)

    /**
     * 更新天气信息
     */
    @Update
    fun updateWeatherForecast(weatherForecast: WeatherForecast)

    /**
     *删除天气信息
     */
    @Query("DELETE FROM WeatherForecast WHERE cityId = :id")
    fun deleteWeatherForecast(id: String)


    /**
     * 查询天气缓存实况
     */
    @Query("SELECT * FROM WeatherLive WHERE cityId = :id")
    fun queryWeatherLive(id: String): WeatherLive

    /**
     *插入或更新城市天气实况
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWeatherLive(weatherLive: WeatherLive)

    /**
     * 更新天气实况
     */
    @Update
    fun updateWeatherLive(weatherLive: WeatherLive)

    /**
     *删除天气实况缓存
     */
    @Query("DELETE FROM WeatherLive WHERE cityId = :id")
    fun deleteWeatherLive(id: String)

}