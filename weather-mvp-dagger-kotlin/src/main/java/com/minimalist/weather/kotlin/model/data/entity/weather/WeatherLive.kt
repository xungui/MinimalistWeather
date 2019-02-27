package com.minimalist.weather.kotlin.model.data.entity.weather

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import android.support.annotation.NonNull

/**
 * 天气实况
 */
@Entity(tableName = "WeatherLive")
class WeatherLive {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = CITY_ID_FIELD_NAME)
    var cityId: String? = null
    @ColumnInfo(name = WEATHER_FIELD_NAME)
    var weather: String? = null//天气情况
    @ColumnInfo(name = TEMP_FIELD_NAME)
    var temp: String? = null//温度
    @ColumnInfo(name = HUMIDITY_FIELD_NAME)
    var humidity: String? = null//湿度
    @ColumnInfo(name = WIND_FIELD_NAME)
    var wind: String? = null//风向
    @ColumnInfo(name = WIND_SPEED_FIELD_NAME)
    var windSpeed: String? = null//风速
    @ColumnInfo(name = TIME_FIELD_NAME)
    var time: Long = 0//发布时间（时间戳）

    @ColumnInfo(name = WIND_POWER_FIELD_NAME)
    var windPower: String? = null//风力
    @ColumnInfo(name = RAIN_FIELD_NAME)
    var rain: String? = null//降雨量(mm)
    @ColumnInfo(name = FEELS_TEMP_FIELD_NAME)
    var feelsTemperature: String? = null//体感温度(℃)
    @ColumnInfo(name = PRESSURE_FIELD_NAME)
    var airPressure: String? = null//气压(hPa)

    @Ignore
    constructor()

    constructor(cityId: String?, weather: String?, temp: String?, humidity: String?, wind: String?,
                windSpeed: String?, time: Long, windPower: String?, rain: String?,
                feelsTemperature: String?, airPressure: String?) {
        this.cityId = cityId
        this.weather = weather
        this.temp = temp
        this.humidity = humidity
        this.wind = wind
        this.windSpeed = windSpeed
        this.time = time
        this.windPower = windPower
        this.rain = rain
        this.feelsTemperature = feelsTemperature
        this.airPressure = airPressure
    }


    override fun toString(): String {
        return "WeatherLive{" +
                "cityId='" + cityId + '\''.toString() +
                ", weather='" + weather + '\''.toString() +
                ", temp='" + temp + '\''.toString() +
                ", humidity='" + humidity + '\''.toString() +
                ", wind='" + wind + '\''.toString() +
                ", windSpeed='" + windSpeed + '\''.toString() +
                ", time=" + time +
                ", windPower='" + windPower + '\''.toString() +
                ", rain='" + rain + '\''.toString() +
                ", feelsTemperature='" + feelsTemperature + '\''.toString() +
                ", airPressure='" + airPressure + '\''.toString() +
                '}'.toString()
    }

    companion object {
        const val CITY_ID_FIELD_NAME = "cityId"
        const val WEATHER_FIELD_NAME = "weather"
        const val TEMP_FIELD_NAME = "temp"
        const val HUMIDITY_FIELD_NAME = "humidity"
        const val WIND_FIELD_NAME = "wind"
        const val WIND_SPEED_FIELD_NAME = "windSpeed"
        const val TIME_FIELD_NAME = "time"

        const val WIND_POWER_FIELD_NAME = "windPower"
        const val RAIN_FIELD_NAME = "rain"
        const val FEELS_TEMP_FIELD_NAME = "feelsTemperature"
        const val PRESSURE_FIELD_NAME = "airPressure"
    }
}
