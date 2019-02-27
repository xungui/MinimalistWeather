package com.minimalist.weather.kotlin.model.http

import com.baronzhang.retrofit2.converter.FastJsonConverterFactory
import com.minimalist.weather.kotlin.BuildConfig

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory

object ApiClient {
    private const val ENVIRONMENT_CLOUD_WEATHER_API_HOST = "http://service.envicloud.cn:8082/"
    private lateinit var environmentCloudWeatherService: EnvironmentCloudWeatherService

    val service: EnvironmentCloudWeatherService
        get() {
            val weatherApiHost = ENVIRONMENT_CLOUD_WEATHER_API_HOST
            environmentCloudWeatherService = initWeatherService(weatherApiHost, EnvironmentCloudWeatherService::class.java)
            return environmentCloudWeatherService
        }

    private fun <T> initWeatherService(baseUrl: String, clazz: Class<T>): T {
        val builder = OkHttpClient().newBuilder()
        if (BuildConfig.DEBUG) {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(httpLoggingInterceptor)
            //builder.addNetworkInterceptor(new StethoInterceptor());
            BuildConfig.STETHO.addNetworkInterceptor(builder)
        }
        val client = builder.build()
        val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(FastJsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(client)
                .build()
        return retrofit.create(clazz)
    }
}

