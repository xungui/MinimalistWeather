package com.minimalist.weather.kotlin.model.data.source.local

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import com.minimalist.weather.kotlin.model.data.entity.city.City

/**
 * city Dao 是本地的数据持久化，只查询数据，不会删除数据，所以只有query方法
 */
@Dao
interface CityDao {
    /**
     *搜索全部保存的城市
     */
    @Query("SELECT * FROM City")
    fun queryAllSaveCity(): List<City>
}