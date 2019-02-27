package com.minimalist.weather.kotlin.model.data.source.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.minimalist.weather.kotlin.model.data.entity.weather.*


/**
 * 天气信息和所属城市的数据库
 */
@Database(entities = arrayOf(AirQualityLive::class, LifeIndex::class,
        Weather::class, WeatherForecast::class, WeatherLive::class), version = 1, exportSchema = false)
abstract class WeatherDatabase : RoomDatabase() {

    abstract fun weatherDao(): WeatherDao

    companion object {
        private const val DATABASE_NAME = "weather.db"
        private var INSTANCE: WeatherDatabase? = null
        private val lock = Any()

        @JvmStatic
        fun getInstance(context: Context): WeatherDatabase {
            synchronized(lock) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                            WeatherDatabase::class.java, DATABASE_NAME)
                            .build()
                }
                return INSTANCE!!
            }
        }
    }
}