package com.minimalist.weather.kotlin.utils

import android.content.Context
import android.net.ConnectivityManager

object NetworkUtils {
    /**
     * 判断网络连接是否可用
     */
    fun isNetworkConnected(context: Context): Boolean {
        val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (manager != null) {
            val networkinfo = manager.activeNetworkInfo
            if (networkinfo != null && networkinfo.isConnected && networkinfo.isAvailable) {
                return true
            }
        }
        return false
    }
}
