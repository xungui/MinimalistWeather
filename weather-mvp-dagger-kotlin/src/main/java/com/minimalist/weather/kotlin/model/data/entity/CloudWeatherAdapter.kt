package com.minimalist.weather.kotlin.model.data.entity

import com.minimalist.weather.kotlin.model.data.entity.weather.*
import com.minimalist.weather.kotlin.model.http.entity.EnvironmentCloudCityAirLive
import com.minimalist.weather.kotlin.model.http.entity.EnvironmentCloudForecast
import com.minimalist.weather.kotlin.model.http.entity.EnvironmentCloudWeatherLive
import com.minimalist.weather.kotlin.utils.DateConvertUtils
import java.util.*

/**
 * 服务端天气信息数据格式转换，存储到本地数据的对象中
 */
class CloudWeatherAdapter(private val cloudWeatherLive: EnvironmentCloudWeatherLive,
                          private val cloudForecast: EnvironmentCloudForecast,
                          private val cloudCityAirLive: EnvironmentCloudCityAirLive) {

    val cityId: String?
        get() = cloudWeatherLive.cityId

    val cityName: String?
        get() = cloudForecast.cityName

    val cityNameEn: String?
        get() = null

    val weatherLive: WeatherLive
        get() {
            val weatherLive = WeatherLive()
            weatherLive.airPressure = cloudWeatherLive.airPressure
            weatherLive.cityId = cloudWeatherLive.cityId
            weatherLive.feelsTemperature = cloudWeatherLive.feelsTemperature
            weatherLive.humidity = cloudWeatherLive.humidity
            weatherLive.rain = cloudWeatherLive.rain
            weatherLive.temp = cloudWeatherLive.temperature
            weatherLive.time = DateConvertUtils.dateToTimeStamp(cloudWeatherLive.updateTime,
                    DateConvertUtils.DATA_FORMAT_PATTEN_YYYY_MM_DD_HH_MM)
            weatherLive.weather = cloudWeatherLive.phenomena
            weatherLive.wind = cloudWeatherLive.windDirect
            weatherLive.windPower = cloudWeatherLive.windPower
            weatherLive.windSpeed = cloudWeatherLive.windSpeed

            return weatherLive
        }

    //            weatherForecast.setWeather();
    val weatherForecasts: List<WeatherForecast>
        get() {
            val weatherForecasts = ArrayList<WeatherForecast>()
            for (forecastEntity in cloudForecast.forecast!!) {
                val weatherForecast = WeatherForecast()
                weatherForecast.wind = forecastEntity.wind!!.dir
                weatherForecast.cityId = cityId
                weatherForecast.humidity = forecastEntity.hum
                weatherForecast.moonrise = forecastEntity.astro!!.mr
                weatherForecast.moonset = forecastEntity.astro!!.ms
                weatherForecast.pop = forecastEntity.pop
                weatherForecast.precipitation = forecastEntity.pcpn
                weatherForecast.pressure = forecastEntity.pres
                weatherForecast.sunrise = forecastEntity.astro!!.sr
                weatherForecast.sunset = forecastEntity.astro!!.ss
                weatherForecast.tempMax = Integer.parseInt(forecastEntity.tmp!!.max!!)
                weatherForecast.tempMin = Integer.parseInt(forecastEntity.tmp!!.min!!)
                weatherForecast.uv = forecastEntity.uv
                weatherForecast.visibility = forecastEntity.vis
                weatherForecast.weatherDay = forecastEntity.cond!!.cond_d
                weatherForecast.weatherNight = forecastEntity.cond!!.cond_n
                weatherForecast.week = DateConvertUtils.convertDataToWeek(forecastEntity.date)
                weatherForecast.date = DateConvertUtils.convertDataToString(forecastEntity.date)
                weatherForecasts.add(weatherForecast)
            }

            return weatherForecasts
        }

    val lifeIndexes: List<LifeIndex>
        get() {
            val suggestionEntity = cloudForecast.suggestion
            val indexList = ArrayList<LifeIndex>()
            val index1 = LifeIndex()
            index1.cityId = cloudForecast.cityId
            index1.name = "空气质量"
            index1.index = suggestionEntity!!.air!!.brf
            index1.details = suggestionEntity.air!!.txt
            indexList.add(index1)

            val index2 = LifeIndex()
            index2.cityId = cloudForecast.cityId
            index2.name = "舒适度"
            index2.index = suggestionEntity.comf!!.brf
            index2.details = suggestionEntity.comf!!.txt
            indexList.add(index2)

            val index3 = LifeIndex()
            index3.cityId = cloudForecast.cityId
            index3.name = "穿衣"
            index3.index = suggestionEntity.drs!!.brf
            index3.details = suggestionEntity.drs!!.txt
            indexList.add(index3)

            val index4 = LifeIndex()
            index4.cityId = cloudForecast.cityId
            index4.name = "感冒"
            index4.index = suggestionEntity.flu!!.brf
            index4.details = suggestionEntity.flu!!.txt
            indexList.add(index4)

            val index5 = LifeIndex()
            index5.cityId = cloudForecast.cityId
            index5.name = "运动"
            index5.index = suggestionEntity.sport!!.brf
            index5.details = suggestionEntity.sport!!.txt
            indexList.add(index5)

            val index6 = LifeIndex()
            index6.cityId = cloudForecast.cityId
            index6.name = "旅游"
            index6.index = suggestionEntity.trav!!.brf
            index6.details = suggestionEntity.trav!!.txt
            indexList.add(index6)

            val index7 = LifeIndex()
            index7.cityId = cloudForecast.cityId
            index7.name = "紫外线"
            index7.index = suggestionEntity.uv!!.brf
            index7.details = suggestionEntity.uv!!.txt
            indexList.add(index7)

            val index8 = LifeIndex()
            index8.cityId = cloudForecast.cityId
            index8.name = "洗车"
            index8.index = suggestionEntity.cw!!.brf
            index8.details = suggestionEntity.cw!!.txt
            indexList.add(index8)

            return indexList
        }

    val airQualityLive: AirQualityLive
        get() {

            val airQualityLive = AirQualityLive()
            airQualityLive.aqi = Integer.parseInt(cloudCityAirLive.aqi!!)
            airQualityLive.cityId = cloudCityAirLive.cityId
            airQualityLive.co = cloudCityAirLive.co
            airQualityLive.no2 = cloudCityAirLive.no2
            airQualityLive.o3 = cloudCityAirLive.o3
            airQualityLive.pm10 = Integer.parseInt(cloudCityAirLive.pm10!!)
            airQualityLive.pm25 = Integer.parseInt(cloudCityAirLive.pm25!!)
            airQualityLive.primary = cloudCityAirLive.primary
            airQualityLive.publishTime = cloudCityAirLive.time
            airQualityLive.quality = getAqiQuality(airQualityLive.aqi)
            airQualityLive.so2 = cloudCityAirLive.so2
            return airQualityLive
        }

    private fun getAqiQuality(aqi: Int): String {
        return when {
            aqi <= 50 -> "优"
            aqi <= 100 -> "良"
            aqi <= 150 -> "轻度污染"
            aqi <= 200 -> "中度污染"
            aqi <= 300 -> "重度污染"
            aqi < 500 -> "严重污染"
            else -> "污染爆表"
        }
    }

    fun getWeather(): Weather {
        val weather = Weather()
        weather.cityId = cityId!!
        weather.cityName = cityName
        weather.cityNameEn = cityNameEn
        weather.airQualityLive = airQualityLive
        weather.weatherForecasts = weatherForecasts
        weather.lifeIndexes = lifeIndexes
        weather.weatherLive = weatherLive
        return weather
    }
}
