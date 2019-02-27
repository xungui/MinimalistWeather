package com.minimalist.weather.kotlin.model.data.entity.weather

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import android.support.annotation.NonNull

/**
 * 天气预报
 */
@Entity(tableName = "WeatherForecast")
class WeatherForecast {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = ID_FIELD_NAME)
    var id: Long = 0//数据库自增长ID
    @ColumnInfo(name = CITY_ID_FIELD_NAME)
    var cityId: String? = null
    @ColumnInfo(name = WEATHER_FIELD_NAME)
    var weather: String? = null
    @ColumnInfo(name = WEATHER_DAY_FIELD_NAME)
    var weatherDay: String? = null
    @ColumnInfo(name = WEATHER_NIGHT_FIELD_NAME)
    var weatherNight: String? = null
    @ColumnInfo(name = TEMP_MAX_FIELD_NAME)
    var tempMax: Int = 0
    @ColumnInfo(name = TEMP_MIN_FIELD_NAME)
    var tempMin: Int = 0
    @ColumnInfo(name = WIND_FIELD_NAME)
    var wind: String? = null
    @ColumnInfo(name = DATE_FIELD_NAME)
    var date: String? = null
    @ColumnInfo(name = WEEK_FIELD_NAME)
    var week: String? = null //周一，周二，...

    @ColumnInfo(name = POP_FIELD_NAME)
    var pop: String? = null//降水概率(%)
    @ColumnInfo(name = UV_FIELD_NAME)
    var uv: String? = null//紫外线级别
    @ColumnInfo(name = VISIBILITY_FIELD_NAME)
    var visibility: String? = null//能见度(km)
    @ColumnInfo(name = HUMIDITY_FIELD_NAME)
    var humidity: String? = null//相对湿度(%)
    @ColumnInfo(name = PRESSURE_FIELD_NAME)
    var pressure: String? = null//气压(hPa)
    @ColumnInfo(name = PRECIPITATION_FIELD_NAME)
    var precipitation: String? = null//降水量(mm)
    @ColumnInfo(name = SUNRISE_FIELD_NAME)
    var sunrise: String? = null//日出
    @ColumnInfo(name = SUNSET_FIELD_NAME)
    var sunset: String? = null//日落
    @ColumnInfo(name = MOONRISE_FIELD_NAME)
    var moonrise: String? = null//月升
    @ColumnInfo(name = MOONSET_FIELD_NAME)
    var moonset: String? = null//月落

    @Ignore
    constructor()

    constructor(id: Long, cityId: String?, weather: String?, weatherDay: String?, weatherNight: String?,
                tempMax: Int, tempMin: Int, wind: String?, date: String?, week: String?, pop: String?,
                uv: String?, visibility: String?, humidity: String?, pressure: String?,
                precipitation: String?, sunrise: String?, sunset: String?, moonrise: String?, moonset: String?) {
        this.id = id
        this.cityId = cityId
        this.weather = weather
        this.weatherDay = weatherDay
        this.weatherNight = weatherNight
        this.tempMax = tempMax
        this.tempMin = tempMin
        this.wind = wind
        this.date = date
        this.week = week
        this.pop = pop
        this.uv = uv
        this.visibility = visibility
        this.humidity = humidity
        this.pressure = pressure
        this.precipitation = precipitation
        this.sunrise = sunrise
        this.sunset = sunset
        this.moonrise = moonrise
        this.moonset = moonset
    }


    override fun toString(): String {
        return "WeatherForecast{" +
                "id=" + id +
                ", cityId='" + cityId + '\''.toString() +
                ", weather='" + weather + '\''.toString() +
                ", weatherDay='" + weatherDay + '\''.toString() +
                ", weatherNight='" + weatherNight + '\''.toString() +
                ", tempMax=" + tempMax +
                ", tempMin=" + tempMin +
                ", wind='" + wind + '\''.toString() +
                ", date='" + date + '\''.toString() +
                ", week='" + week + '\''.toString() +
                ", pop='" + pop + '\''.toString() +
                ", uv='" + uv + '\''.toString() +
                ", visibility='" + visibility + '\''.toString() +
                ", humidity='" + humidity + '\''.toString() +
                ", pressure='" + pressure + '\''.toString() +
                ", precipitation='" + precipitation + '\''.toString() +
                ", sunrise='" + sunrise + '\''.toString() +
                ", sunset='" + sunset + '\''.toString() +
                ", moonrise='" + moonrise + '\''.toString() +
                ", moonset='" + moonset + '\''.toString() +
                '}'.toString()
    }

    companion object {

        const val ID_FIELD_NAME = "_id"
        const val CITY_ID_FIELD_NAME = "cityId"
        const val WEATHER_FIELD_NAME = "weather"
        const val WEATHER_DAY_FIELD_NAME = "weatherDay"
        const val WEATHER_NIGHT_FIELD_NAME = "weatherNight"
        const val TEMP_MAX_FIELD_NAME = "tempMax"
        const val TEMP_MIN_FIELD_NAME = "tempMin"
        const val WIND_FIELD_NAME = "wind"
        const val DATE_FIELD_NAME = "date"
        const val WEEK_FIELD_NAME = "week"

        const val POP_FIELD_NAME = "pop"
        const val UV_FIELD_NAME = "uv"
        const val VISIBILITY_FIELD_NAME = "visibility"
        const val HUMIDITY_FIELD_NAME = "humidity"
        const val PRESSURE_FIELD_NAME = "pressure"
        const val PRECIPITATION_FIELD_NAME = "precipitation"
        const val SUNRISE_FIELD_NAME = "sunrise"
        const val SUNSET_FIELD_NAME = "sunset"
        const val MOONRISE_FIELD_NAME = "moonrise"
        const val MOONSET_FIELD_NAME = "moonset"
    }
}
