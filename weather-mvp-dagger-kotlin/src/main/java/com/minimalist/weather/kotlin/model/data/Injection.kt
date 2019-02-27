/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.minimalist.weather.kotlin.model.data

import android.content.Context

import com.minimalist.weather.kotlin.model.data.source.CityDataRepository
import com.minimalist.weather.kotlin.model.data.source.WeatherDataRepository
import com.minimalist.weather.kotlin.model.data.source.local.CityDatabase
import com.minimalist.weather.kotlin.model.data.source.local.CityLocalDataSource
import com.minimalist.weather.kotlin.model.data.source.local.WeatherDatabase
import com.minimalist.weather.kotlin.model.data.source.local.WeatherLocalDataSource
import com.minimalist.weather.kotlin.model.data.source.remote.WeatherRemoteDataSource
import com.minimalist.weather.kotlin.utils.AppExecutors

object Injection {

    fun provideCityRepository(context: Context): CityDataRepository {
        val database = CityDatabase.getInstance(context)
        return CityDataRepository.getInstance(CityLocalDataSource.getInstance(AppExecutors(),
                database.cityDao()))
    }

    fun provideWeatherRepository(context: Context): WeatherDataRepository {
        val database = WeatherDatabase.getInstance(context)
        return WeatherDataRepository.getInstance(
                WeatherLocalDataSource.getInstance(AppExecutors(), database.weatherDao()),
                WeatherRemoteDataSource.getInstance(AppExecutors(), database.weatherDao())
        )
    }
}
