package com.minimalist.weather.kotlin.utils.system

import android.app.Activity

internal interface SystemHelper {

    fun setStatusBarLightMode(activity: Activity, isFontColorDark: Boolean): Boolean
}
