package com.minimalist.weather.kotlin.model.data.entity.weather

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import android.support.annotation.NonNull

@Entity(tableName = "AirQuality")
class AirQualityLive {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = CITY_ID_FIELD_NAME)
    var cityId: String? = null
    @ColumnInfo(name = AQI_FIELD_NAME)
    var aqi: Int = 0
    @ColumnInfo(name = PM25_FIELD_NAME)
    var pm25: Int = 0
    @ColumnInfo(name = PM10_FIELD_NAME)
    var pm10: Int = 0
    @ColumnInfo(name = PUBLISH_TIME_FIELD_NAME)
    var publishTime: String? = null
    @ColumnInfo(name = ADVICE_FIELD_NAME)
    var advice: String? = null//建议
    @ColumnInfo(name = CITY_RANK_FIELD_NAME)
    var cityRank: String? = null//城市排名
    @ColumnInfo(name = QUALITY_FIELD_NAME)
    var quality: String? = null//空气质量
    @ColumnInfo(name = CO_FIELD_NAME)
    var co: String? = null//一氧化碳浓度(mg/m3)
    @ColumnInfo(name = SO2_FIELD_NAME)
    var so2: String? = null//二氧化硫浓度(μg/m3)
    @ColumnInfo(name = NO2_FIELD_NAME)
    var no2: String? = null//二氧化氮浓度(μg/m3)
    @ColumnInfo(name = O3_FIELD_NAME)
    var o3: String? = null//臭氧浓度(μg/m3)
    @ColumnInfo(name = PRIMARY_FIELD_NAME)
    var primary: String? = null//首要污染物

    @Ignore
    constructor()

    constructor(cityId: String?, aqi: Int, pm25: Int, pm10: Int, publishTime: String?,
                advice: String?, cityRank: String?, quality: String?, co: String?,
                so2: String?, no2: String?, o3: String?, primary: String?) {
        this.cityId = cityId
        this.aqi = aqi
        this.pm25 = pm25
        this.pm10 = pm10
        this.publishTime = publishTime
        this.advice = advice
        this.cityRank = cityRank
        this.quality = quality
        this.co = co
        this.so2 = so2
        this.no2 = no2
        this.o3 = o3
        this.primary = primary
    }


    companion object {
        const val CITY_ID_FIELD_NAME = "cityId"
        const val AQI_FIELD_NAME = "aqi"
        const val PM25_FIELD_NAME = "pm25"
        const val PM10_FIELD_NAME = "pm10"
        const val PUBLISH_TIME_FIELD_NAME = "publishTime"
        const val ADVICE_FIELD_NAME = "advice"
        const val CITY_RANK_FIELD_NAME = "cityRank"
        const val QUALITY_FIELD_NAME = "quality"

        const val CO_FIELD_NAME = "co"
        const val SO2_FIELD_NAME = "so2"
        const val NO2_FIELD_NAME = "no2"
        const val O3_FIELD_NAME = "o3"
        const val PRIMARY_FIELD_NAME = "primary"
    }
}
