package com.weather.mausamkikhabar

import android.app.Application
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.weather.mausamkikhabar.data.retrofit.RetrofitClient

/**
 * Created on 01-Jul-19.
 */
class WeatherApplication : Application() {

    private var sharedPreferences: SharedPreferences? = null

    override fun onCreate() {
        super.onCreate()

        instance = this
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)

        RetrofitClient.create()
    }

    companion object {

        var instance: WeatherApplication? = null
            private set
    }

    fun getSharedPreferences(): SharedPreferences {

        return sharedPreferences!!
    }

}