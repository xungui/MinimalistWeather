package com.minimalist.weather.kotlin.model.data.entity.city

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "City")
class City {
    @PrimaryKey
    @ColumnInfo(name = ID_FIELD_NAME)
    var id: Int = 0
    @ColumnInfo(name = ROOT_FIELD_NAME)
    var root: String? = null
    @ColumnInfo(name = PARENT_FIELD_NAME)
    var parent: String? = null
    @ColumnInfo(name = CITY_ID_FIELD_NAME)
    var cityId: Int = 0
    @ColumnInfo(name = CITY_NAME_FIELD_NAME)
    var cityName: String? = null
    @ColumnInfo(name = CITY_NAME_EN_FIELD_NAME)
    var cityNameEn: String? = null
    @ColumnInfo(name = LON_FIELD_NAME)
    var lon: String? = null
    @ColumnInfo(name = LAT_FIELD_NAME)
    var lat: String? = null

    constructor(root: String?, parent: String?, cityId: Int, cityName: String?,
                cityNameEn: String?, lon: String?, lat: String?) {
        this.root = root
        this.parent = parent
        this.cityId = cityId
        this.cityName = cityName
        this.cityNameEn = cityNameEn
        this.lon = lon
        this.lat = lat
    }


    override fun toString(): String {
        return "CityInfo{" +
                "id=" + id +
                ", root='" + root + '\''.toString() +
                ", parent='" + parent + '\''.toString() +
                ", cityId='" + cityId + '\''.toString() +
                ", cityName='" + cityName + '\''.toString() +
                ", cityNameEn='" + cityNameEn + '\''.toString() +
                ", lon='" + lon + '\''.toString() +
                ", lat='" + lat + '\''.toString() +
                '}'.toString()
    }

    companion object {
        const val ID_FIELD_NAME = "_id"
        const val ROOT_FIELD_NAME = "root"
        const val PARENT_FIELD_NAME = "parent"
        const val CITY_NAME_FIELD_NAME = "name"
        const val CITY_NAME_EN_FIELD_NAME = "pinyin"
        const val LON_FIELD_NAME = "x"
        const val LAT_FIELD_NAME = "y"
        const val CITY_ID_FIELD_NAME = "posID"
    }
}
