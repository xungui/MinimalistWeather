package com.minimalist.weather.kotlin.model.http

import com.minimalist.weather.kotlin.model.http.entity.EnvironmentCloudCityAirLive
import com.minimalist.weather.kotlin.model.http.entity.EnvironmentCloudForecast
import com.minimalist.weather.kotlin.model.http.entity.EnvironmentCloudWeatherLive
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface EnvironmentCloudWeatherService {

    /**
     * 获取指定城市的实时天气
     *
     *
     * API地址：http://service.envicloud.cn:8082/v2/weatherlive/YMFYB256AGFUZZE0ODQ3MZM1MZE2NTU=/101020100
     *
     * @param cityId 城市id
     * @return Observable
     */
    @GET("/v2/weatherlive/YMFYB256AGFUZZE0ODQ3MZM1MZE2NTU=/{cityId}")
    fun getWeatherLive(@Path("cityId") cityId: String): Call<EnvironmentCloudWeatherLive>

    /**
     * 获取指定城市7日天气预报
     *
     *
     * API地址：http://service.envicloud.cn:8082/v2/weatherforecast/YMFYB256AGFUZZE0ODQ3MZM1MZE2NTU=/101020100
     *
     * @param cityId 城市id
     * @return Observable
     */
    @GET("/v2/weatherforecast/YMFYB256AGFUZZE0ODQ3MZM1MZE2NTU=/{cityId}")
    fun getWeatherForecast(@Path("cityId") cityId: String): Call<EnvironmentCloudForecast>

    /**
     * 获取指定城市的实时空气质量
     *
     *
     * API地址：http://service.envicloud.cn:8082/v2/cityairlive/YMFYB256AGFUZZE0ODQ3MZM1MZE2NTU=/101020100
     *
     * @param cityId 城市id
     * @return Observable
     */
    @GET("/v2/cityairlive/YMFYB256AGFUZZE0ODQ3MZM1MZE2NTU=/{cityId}")
    fun getAirLive(@Path("cityId") cityId: String): Call<EnvironmentCloudCityAirLive>
}
