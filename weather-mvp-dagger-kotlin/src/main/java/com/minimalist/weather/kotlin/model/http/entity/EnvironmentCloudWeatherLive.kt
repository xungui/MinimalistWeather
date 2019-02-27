package com.minimalist.weather.kotlin.model.http.entity

import com.alibaba.fastjson.annotation.JSONField

class EnvironmentCloudWeatherLive {
    /**
     * airpressure : 1016.0
     * rain : 0.0
     * windpower : 微风
     * rcode : 200
     * feelst : 17.7
     * citycode : 101020100
     * rdesc : Success
     * winddirect : 西北风
     * temperature : 17.8
     * humidity : 50.0
     * windspeed : 0.9
     * updatetime : 2017-02-16 14:06
     * phenomena : 阵雨
     */

    @JSONField(name = "rcode")
    var requestCode: Int = 0//结果吗

    @JSONField(name = "rdesc")
    var requestDesc: String? = null//结果描述

    @JSONField(name = "updatetime")
    var updateTime: String? = null//更新时间

    var phenomena: String? = null//天气现象

    var temperature: String? = null//气温(℃)

    @JSONField(name = "feelst")
    var feelsTemperature: String? = null//体感温度(℃)

    @JSONField(name = "airpressure")
    var airPressure: String? = null//气压(hPa)

    var humidity: String? = null//相对湿度(%)

    var rain: String? = null//降雨量(mm)

    @JSONField(name = "winddirect")
    var windDirect: String? = null//风向

    @JSONField(name = "windpower")
    var windPower: String? = null//风力

    @JSONField(name = "windspeed")
    var windSpeed: String? = null//风速(m/s)

    @JSONField(name = "citycode")
    var cityId: String? = null//城市ID
}

