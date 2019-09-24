package com.weather.mausamkikhabar.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import com.weather.mausamkikhabar.constants.AppConstants
import com.weather.mausamkikhabar.data.model.ForecastInfo
import com.weather.mausamkikhabar.data.model.WeatherInfo
import com.weather.mausamkikhabar.data.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created on 19-Jul-19.
 */
class MainActivityViewModel(application: Application) : AndroidViewModel(application) {

        fun getWeatherInfo(cityId: Int) {

        val call = RetrofitClient.getApiInterface().getWeatherForCityId(cityId, AppConstants.API_KEY)

        call.enqueue(object : Callback<WeatherInfo> {
            override fun onFailure(call: Call<WeatherInfo>, t: Throwable) {


            }

            override fun onResponse(call: Call<WeatherInfo>, response: Response<WeatherInfo>) {


            }
        })

    }

}