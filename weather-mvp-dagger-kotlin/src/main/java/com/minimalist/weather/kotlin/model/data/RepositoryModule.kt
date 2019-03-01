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
    internal abstract fun provideCityLocalDataSource(dataSource: CityLocalDataSource): CityDataSource

    @Singleton
    @Binds
    @Local
    internal abstract fun provideWeatherLocalDataSource(dataSource: WeatherLocalDataSource): WeatherDataSource

    @Singleton
    @Binds
    @Remote
    internal abstract fun provideWeatherRemoteDataSource(dataSource: WeatherRemoteDataSource): WeatherDataSource

    companion object {
        private val THREAD_COUNT = 3

        @Singleton
        @Provides
        internal fun provideCityDb(context: Application): CityDatabase {
            return Room.databaseBuilder(context.applicationContext, CityDatabase::class.java, CityDatabase.DATABASE_NAME)
                    .build()
        }

        @Singleton
        @Provides
        internal fun provideWeatherDb(context: Application): WeatherDatabase {
            return Room.databaseBuilder(context.applicationContext, WeatherDatabase::class.java, WeatherDatabase.DATABASE_NAME)
                    .build()
        }

        @Singleton
        @Provides
        internal fun provideCityDao(db: CityDatabase): CityDao {
            return db.cityDao()
        }

        @Singleton
        @Provides
        internal fun provideWeatherDao(db: WeatherDatabase): WeatherDao {
            return db.weatherDao()
        }

        @Singleton
        @Provides
        internal fun provideAppExecutors(): AppExecutors {
            return AppExecutors(DiskIOThreadExecutor(),
                    Executors.newFixedThreadPool(THREAD_COUNT),
                    AppExecutors.MainThreadExecutor())
        }
    }
}
