package com.minimalist.weather.kotlin.model.data.source.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.minimalist.weather.kotlin.model.data.entity.weather.*


/**
 * 天气信息和所属城市的数据库
 */
@Database(entities = arrayOf(AirQualityLive::class, LifeIndex::class,
        Weather::class, WeatherForecast::class, WeatherLive::class), version = 1, exportSchema = false)
abstract class WeatherDatabase : RoomDatabase() {

    abstract fun weatherDao(): WeatherDao

    companion object {
        const val DATABASE_NAME = "weather.db"
    }
}