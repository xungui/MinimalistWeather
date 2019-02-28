package com.minimalist.weather.kotlin.model.data;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.minimalist.weather.kotlin.model.data.source.CityDataSource;
import com.minimalist.weather.kotlin.model.data.source.WeatherDataSource;
import com.minimalist.weather.kotlin.model.data.source.local.CityDao;
import com.minimalist.weather.kotlin.model.data.source.local.CityDatabase;
import com.minimalist.weather.kotlin.model.data.source.local.CityLocalDataSource;
import com.minimalist.weather.kotlin.model.data.source.local.WeatherDao;
import com.minimalist.weather.kotlin.model.data.source.local.WeatherDatabase;
import com.minimalist.weather.kotlin.model.data.source.local.WeatherLocalDataSource;
import com.minimalist.weather.kotlin.model.data.source.remote.WeatherRemoteDataSource;
import com.minimalist.weather.kotlin.utils.AppExecutors;
import com.minimalist.weather.kotlin.utils.DiskIOThreadExecutor;

import java.util.concurrent.Executors;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
abstract public class RepositoryModule {
    private static final int THREAD_COUNT = 3;

    @Singleton
    @Binds
    abstract CityDataSource provideCityLocalDataSource(CityLocalDataSource dataSource);

    @Singleton
    @Binds
    @Local
    abstract WeatherDataSource provideWeatherLocalDataSource(WeatherLocalDataSource dataSource);

    @Singleton
    @Binds
    @Remote
    abstract WeatherDataSource provideWeatherRemoteDataSource(WeatherRemoteDataSource dataSource);

    @Singleton
    @Provides
    static CityDatabase provideCityDb(Application context) {
        return Room.databaseBuilder(context.getApplicationContext(), CityDatabase.class, CityDatabase.DATABASE_NAME)
                .build();
    }

    @Singleton
    @Provides
    static WeatherDatabase provideWeatherDb(Application context) {
        return Room.databaseBuilder(context.getApplicationContext(), WeatherDatabase.class, WeatherDatabase.DATABASE_NAME)
                .build();
    }

    @Singleton
    @Provides
    static CityDao provideCityDao(CityDatabase db) {
        return db.cityDao();
    }

    @Singleton
    @Provides
    static WeatherDao provideWeatherDao(WeatherDatabase db) {
        return db.weatherDao();
    }

    @Singleton
    @Provides
    static AppExecutors provideAppExecutors() {
        return new AppExecutors(new DiskIOThreadExecutor(),
                Executors.newFixedThreadPool(THREAD_COUNT),
                new AppExecutors.MainThreadExecutor());
    }
}
