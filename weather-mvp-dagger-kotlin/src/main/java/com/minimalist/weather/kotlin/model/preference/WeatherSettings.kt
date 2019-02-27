package com.minimalist.weather.kotlin.model.preference

enum class WeatherSettings private constructor(val id: String, val defaultValue: Any) {
    /*默认配置项*/
    SETTINGS_FIRST_USE("first_use", java.lang.Boolean.TRUE),
    SETTINGS_CURRENT_CITY_ID("current_city_id", "");


    companion object {

        fun fromId(id: String): WeatherSettings? {
            val values = values()
            for (value in values) {
                if (value.id == id) {
                    return value
                }
            }
            return null
        }
    }
}
