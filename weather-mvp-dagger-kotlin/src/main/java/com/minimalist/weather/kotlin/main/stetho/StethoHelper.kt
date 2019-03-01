package com.minimalist.weather.kotlin.main.stetho

import android.content.Context

import okhttp3.OkHttpClient

interface StethoHelper {

    fun init(context: Context)

    fun addNetworkInterceptor(builder: OkHttpClient.Builder): OkHttpClient.Builder?
}
