package com.minimalist.weather.kotlin.model.data.entity.weather

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import android.support.annotation.NonNull

/**
 *天气实况和预报的集合
 */
@Entity(tableName = "Weather")
class Weather {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = CITY_ID_FIELD_NAME)
    lateinit var cityId: String
    @ColumnInfo(name = CITY_NAME_FIELD_NAME)
    var cityName: String? = null
    @ColumnInfo(name = CITY_NAME_EN_FIELD_NAME)
    var cityNameEn: String? = null

    @Ignore
    var weatherLive: WeatherLive? = null
    @Ignore
    var weatherForecasts: List<WeatherForecast>? = null
    @Ignore
    var airQualityLive: AirQualityLive? = null
    @Ignore
    var lifeIndexes: List<LifeIndex>? = null

    @Ignore
    constructor()

    constructor(cityId: String, cityName: String?, cityNameEn: String?) {
        this.cityId = cityId
        this.cityName = cityName
        this.cityNameEn = cityNameEn
    }

    override fun toString(): String {
        return "WeatherData{" +
                "aqi=" + airQualityLive +
                ", cityId='" + cityId + '\''.toString() +
                ", cityName='" + cityName + '\''.toString() +
                ", cityNameEn='" + cityNameEn + '\''.toString() +
                ", realTime=" + weatherLive +
                ", forecasts=" + weatherForecasts +
                ", lifeIndexes=" + lifeIndexes +
                '}'.toString()
    }

    companion object {
        const val CITY_ID_FIELD_NAME = "cityId"
        const val CITY_NAME_FIELD_NAME = "cityName"
        const val CITY_NAME_EN_FIELD_NAME = "cityNameEn"
    }
}
