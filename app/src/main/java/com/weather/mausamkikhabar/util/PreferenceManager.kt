package com.weather.mausamkikhabar.util

import com.weather.mausamkikhabar.WeatherApplication


/**
 * Created on 26,December,2018
 *
 */
object PreferenceManager {

    const val SAVED_CITIES_LIST = "savedCitiesList"

    fun setInt(key: String, value: Int) {

        WeatherApplication.instance?.getSharedPreferences()?.edit()?.putInt(key, value)?.apply()
    }

    fun getInt(key: String): Int? {

        return WeatherApplication.instance?.getSharedPreferences()?.getInt(key, -1)
    }

    fun setString(key: String, value: String) {

        WeatherApplication.instance?.getSharedPreferences()?.edit()?.putString(key, value)?.apply()
    }

    fun getString(key: String): String {

        val value = WeatherApplication.instance?.getSharedPreferences()?.getString(key, null)

        return if (value.isNullOrBlank()) "" else value
    }

    fun setBoolean(key: String, value: Boolean) {

        WeatherApplication.instance?.getSharedPreferences()?.edit()?.putBoolean(key, value)?.apply()
    }

    fun getBoolean(key: String): Boolean? {

        return WeatherApplication.instance?.getSharedPreferences()?.getBoolean(key, false)
    }

    fun deleteAll() {
        WeatherApplication.instance?.getSharedPreferences()?.edit()?.clear()?.apply()
    }

}