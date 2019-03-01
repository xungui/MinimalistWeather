package com.minimalist.weather.kotlin.model.data

import android.app.Application
import android.arch.persistence.room.Room
import com.minimalist.weather.kotlin.model.data.source.CityDataSource
import com.minimalist.weather.kotlin.model.data.source.WeatherDataSource
import com.minimalist.weather.kotlin.model.data.source.local.*
import com.minimalist.weather.kotlin.model.data.source.remote.WeatherRemoteDataSource
import com.minimalist.weather.kotlin.utils.AppExecutors
import com.minimalist.weather.kotlin.utils.DiskIOThreadExecutor
import dagger.Binds
import dagger.Module
import dagger.Provides
import java.util.concurrent.Executors
import javax.inject.Singleton

@Module
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun provideCityLocalDataSource(dataSource: CityLocalDataSource): CityDataSource

    @Singleton
    @Binds
    @Local
    abstract fun provideWeatherLocalDataSource(dataSource: WeatherLocalDataSource): WeatherDataSource

    @Singleton
    @Binds
    @Remote
    abstract fun provideWeatherRemoteDataSource(dataSource: WeatherRemoteDataSource): WeatherDataSource

    @Module
    companion object {
        private const val THREAD_COUNT = 3

        @JvmStatic
        @Singleton
        @Provides
        fun provideCityDb(context: Application): CityDatabase {
            return Room.databaseBuilder(context.applicationContext, CityDatabase::class.java, CityDatabase.DATABASE_NAME)
                    .build()
        }

        @JvmStatic
        @Singleton
        @Provides
        fun provideWeatherDb(context: Application): WeatherDatabase {
            return Room.databaseBuilder(context.applicationContext, WeatherDatabase::class.java, WeatherDatabase.DATABASE_NAME)
                    .build()
        }

        @JvmStatic
        @Singleton
        @Provides
        fun provideCityDao(db: CityDatabase): CityDao {
            return db.cityDao()
        }

        @JvmStatic
        @Singleton
        @Provides
        fun provideWeatherDao(db: WeatherDatabase): WeatherDao {
            return db.weatherDao()
        }

        @JvmStatic
        @Singleton
        @Provides
        fun provideAppExecutors(): AppExecutors {
            return AppExecutors(DiskIOThreadExecutor(),
                    Executors.newFixedThreadPool(THREAD_COUNT),
                    AppExecutors.MainThreadExecutor())
        }
    }
}
