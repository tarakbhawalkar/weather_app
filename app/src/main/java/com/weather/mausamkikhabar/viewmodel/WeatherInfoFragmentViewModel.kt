package com.weather.mausamkikhabar.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.weather.mausamkikhabar.constants.AppConstants
import com.weather.mausamkikhabar.data.model.ForecastInfo
import com.weather.mausamkikhabar.data.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created on 23-Jul-19.
 */
class WeatherInfoFragmentViewModel(application: Application) : AndroidViewModel(application) {

    private var forecastInfoLiveData = MutableLiveData<ForecastInfo>()

    fun getForecastInfo(cityId: Int) {
        val call = RetrofitClient.getApiInterface().getWeatherForecastForCityId(cityId, AppConstants.API_KEY)

        call.enqueue(object : Callback<ForecastInfo> {
            override fun onFailure(call: Call<ForecastInfo>, t: Throwable) {
                Log.e("onFailure", "onFailure")
            }

            override fun onResponse(call: Call<ForecastInfo>, response: Response<ForecastInfo>) {

                forecastInfoLiveData.postValue(response.body())
            }
        })
    }

    fun getForecastInfoLiveData(): MutableLiveData<ForecastInfo> {
        return forecastInfoLiveData
    }
}