package com.minimalist.weather.kotlin.model.preference

import android.content.Context
import android.content.SharedPreferences
import android.util.Log


import com.minimalist.weather.kotlin.main.WeatherApplication

import java.io.InvalidClassException
import java.util.ArrayList
import java.util.Collections
import java.util.HashMap

class PreferenceHelper private constructor() {
    companion object {
        private val TAG = "Preferences"
        private val SETTINGS_FILENAME = WeatherApplication::class.java.getPackage()!!.name
        private val CONFIGURATION_LISTENERS = Collections.synchronizedList(ArrayList<ConfigurationListener>())

        fun loadDefaults() {
            //设置SharedPreferences默认值
            try {
                val defaultPrefs = HashMap<WeatherSettings, Any>()
                val values = WeatherSettings.values()
                for (value in values) {
                    defaultPrefs[value] = value.defaultValue
                }
                savePreferences(defaultPrefs, true)
            } catch (ex: Exception) {
                Log.e(TAG, "Save default settings fails", ex)
            }

        }

        fun addConfigurationListener(listener: ConfigurationListener) {
            CONFIGURATION_LISTENERS!!.add(listener)
        }

        fun removeConfigurationListener(listener: ConfigurationListener) {
            CONFIGURATION_LISTENERS!!.remove(listener)
        }

        val sharedPreferences: SharedPreferences
            get() = WeatherApplication.instance!!.getSharedPreferences(
                    SETTINGS_FILENAME, Context.MODE_PRIVATE)

        @Throws(InvalidClassException::class)
        fun savePreference(pref: WeatherSettings, value: Any) {
            val prefs = HashMap<WeatherSettings, Any>()
            prefs[pref] = value
            savePreferences(prefs, false)
        }

        @Throws(InvalidClassException::class)
        fun savePreferences(prefs: Map<WeatherSettings, Any>) {

            savePreferences(prefs, false)
        }

        @Throws(InvalidClassException::class)
        private fun savePreferences(prefs: Map<WeatherSettings, Any>, noSaveIfExists: Boolean) {

            val sp = sharedPreferences
            val editor = sp.edit()

            for (pref in prefs.keys) {

                val value = prefs[pref]

                if (noSaveIfExists && sp.contains(pref.id)) {
                    continue
                }

                if (value is Boolean && pref.defaultValue is Boolean) {
                    editor.putBoolean(pref.id, (value as Boolean?)!!)
                } else if (value is String && pref.defaultValue is String) {
                    editor.putString(pref.id, value as String?)
                } else if (value is Set<*> && pref.defaultValue is Set<*>) {
                    editor.putStringSet(pref.id, value as Set<String>?)
                } else if (value is Int && pref.defaultValue is Int) {
                    editor.putInt(pref.id, (value as Int?)!!)
                } else if (value is Float && pref.defaultValue is Float) {
                    editor.putFloat(pref.id, (value as Float?)!!)
                } else if (value is Long && pref.defaultValue is Long) {
                    editor.putLong(pref.id, (value as Long?)!!)
                } else {
                    //The object is not of the appropriate type
                    val msg = String.format("%s: %s", pref.id, value!!.javaClass.name)
                    Log.e(TAG, String.format("Configuration error. InvalidClassException: %s", msg))
                    throw InvalidClassException(msg)
                }
            }

            editor.apply()

            if (CONFIGURATION_LISTENERS != null && CONFIGURATION_LISTENERS.size > 0) {
                for (pref in prefs.keys) {
                    val value = prefs[pref]
                    for (listener in CONFIGURATION_LISTENERS) {
                        listener.onConfigurationChanged(pref, value!!)
                    }
                }
            }
        }
    }
}
