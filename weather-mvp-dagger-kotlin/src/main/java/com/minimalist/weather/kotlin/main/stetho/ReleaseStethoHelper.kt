package com.minimalist.weather.kotlin.main.stetho

import android.content.Context

import okhttp3.OkHttpClient

class ReleaseStethoHelper : StethoHelper {

    override fun init(context: Context) {

    }

    override fun addNetworkInterceptor(builder: OkHttpClient.Builder): OkHttpClient.Builder? {
        return null
    }
}
