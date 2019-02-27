package com.minimalist.weather.kotlin.model.data.entity.weather

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "LifeIndex")
class LifeIndex {
    @PrimaryKey
    @ColumnInfo(name = ID_FIELD_NAME)
    var id: Long = 0//数据库自增长ID
    @ColumnInfo(name = CITY_ID_FIELD_NAME)
    var cityId: String? = null
    @ColumnInfo(name = NAME_ID_FIELD_NAME)
    var name: String? = null
    @ColumnInfo(name = INDEX_ID_FIELD_NAME)
    var index: String? = null
    @ColumnInfo(name = DETAILS_ID_FIELD_NAME)
    var details: String? = null

    @Ignore
    constructor()

    constructor(id: Long, cityId: String?, name: String?, index: String?, details: String?) {
        this.id = id
        this.cityId = cityId
        this.name = name
        this.index = index
        this.details = details
    }

    override fun toString(): String {
        return "LifeIndex{" +
                "id=" + id +
                ", cityId=" + cityId +
                ", name='" + name + '\''.toString() +
                ", index='" + index + '\''.toString() +
                ", details='" + details + '\''.toString() +
                '}'.toString()
    }

    companion object {
        const val ID_FIELD_NAME = "_id"
        const val CITY_ID_FIELD_NAME = "cityId"
        const val NAME_ID_FIELD_NAME = "name"
        const val INDEX_ID_FIELD_NAME = "index"
        const val DETAILS_ID_FIELD_NAME = "details"
    }
}
