package com.weather.mausamkikhabar.util

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.weather.mausamkikhabar.data.model.City
import java.io.IOException
import java.io.InputStream


/**
 * Created on 19-Jul-19.
 */
object CityListHelper {

    fun getCities(context: Context): MutableList<City> {
        var json: String? = null
        try {
            val inputStream: InputStream = context.assets.open("CityList.json")
            json = inputStream.bufferedReader().use { it.readText() }
        } catch (ex: IOException) {
            ex.printStackTrace()
        }

        val gson = Gson()
        val listType = object : TypeToken<MutableList<City>>() {

        }.type
        val list: MutableList<City> = gson.fromJson(json, listType)
        val filteredList: MutableList<City> =
            list.filter { it.country.equals("IN", false) }.toMutableList()
        filteredList.sortBy { it.name }
        return filteredList
    }

    fun getSavedCitiesList(): MutableList<City> {

        val gson = Gson()
        val listType = object : TypeToken<MutableList<City>>() {
        }.type
        return if (!PreferenceManager.getString(PreferenceManager.SAVED_CITIES_LIST)?.isBlank())

            gson.fromJson(
                PreferenceManager.getString(PreferenceManager.SAVED_CITIES_LIST)
                , listType
            )
        else {
            mutableListOf()
        }
    }

    fun getJsonString(city: City): String {

        return Gson().toJson(city).toString()
    }

    fun getJsonString(cities: List<City>): String {
        return Gson().toJson(cities).toString()

    }


}