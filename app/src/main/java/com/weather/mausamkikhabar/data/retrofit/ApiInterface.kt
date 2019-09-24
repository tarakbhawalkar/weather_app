package com.weather.mausamkikhabar.data.retrofit

import com.weather.mausamkikhabar.data.model.ForecastInfo
import com.weather.mausamkikhabar.data.model.WeatherInfo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("weather")
    fun getWeatherForCityId(
        @Query("id") cityId: Int,
        @Query("appid") appId: String
    ): Call<WeatherInfo>

    @GET("forecast/daily")
    fun getWeatherForecastForCityId(
        @Query("id") cityId: Int,
        @Query("appid") appId: String,
        @Query("lang") language: String = "en"
    ): Call<ForecastInfo>
}