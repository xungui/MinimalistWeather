package com.minimalist.weather.kotlin.utils.stetho

import android.content.Context

import com.facebook.stetho.Stetho
import com.facebook.stetho.okhttp3.StethoInterceptor

import okhttp3.OkHttpClient

class DebugStethoHelper : StethoHelper {

    override fun init(context: Context) {
        Stetho.initializeWithDefaults(context)
    }

    override fun addNetworkInterceptor(builder: OkHttpClient.Builder): OkHttpClient.Builder {
        return builder.addNetworkInterceptor(StethoInterceptor())
    }
}
