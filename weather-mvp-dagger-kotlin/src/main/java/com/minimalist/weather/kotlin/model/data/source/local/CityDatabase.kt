package com.minimalist.weather.kotlin.model.data.source.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.minimalist.weather.kotlin.R
import com.minimalist.weather.kotlin.main.WeatherApplication
import com.minimalist.weather.kotlin.model.data.entity.city.City
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

@Database(entities = [City::class], version = 1,exportSchema=false)
abstract class CityDatabase : RoomDatabase() {

    abstract fun cityDao(): CityDao

    companion object {
        private const val DATABASE_NAME = "city.db"
        private var INSTANCE: CityDatabase? = null
        private val lock = Any()

        @JvmStatic
        fun getInstance(context: Context): CityDatabase {
            synchronized(lock) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                            CityDatabase::class.java, DATABASE_NAME)
                            .build()
                }
                return INSTANCE!!
            }
        }

        /**
         * 导入城市数据库
         */
        fun importCityDB() {
            val application = WeatherApplication.instance
            // 判断保持城市的数据库文件是否存在
            val file = File(application!!.getDatabasePath(DATABASE_NAME).absolutePath)
            if (!file.exists()) {// 如果不存在，则导入数据库文件
                //数据库文件
                val dbFile = application.getDatabasePath(DATABASE_NAME)
                try {
                    if (!dbFile.parentFile.exists()) {
                        dbFile.parentFile.mkdir()
                    }
                    if (!dbFile.exists()) {
                        dbFile.createNewFile()
                    }
                    //加载欲导入的数据库
                    val `is` = application.resources.openRawResource(R.raw.city)
                    val fos = FileOutputStream(dbFile)
                    val buffer = ByteArray(`is`.available())
                    `is`.read(buffer)
                    fos.write(buffer)
                    `is`.close()
                    fos.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }

}