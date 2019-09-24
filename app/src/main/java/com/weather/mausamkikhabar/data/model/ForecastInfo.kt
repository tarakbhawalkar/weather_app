package com.weather.mausamkikhabar.data.model

import com.google.gson.annotations.SerializedName


/**
 * Created on 22-Jul-19.
 */
data class ForecastInfo(
    @SerializedName("cod")
    var cod: String? = "",
    @SerializedName("message")
    var message: Double? = 0.0,
    @SerializedName("city")
    var city: City? = City(),
    @SerializedName("cnt")
    var cnt: Int? = 0,
    @SerializedName("list")
    var list: ArrayList<ForecastData?>? = arrayListOf()
)

data class ForecastData(
    @SerializedName("dt")
    var dt: Long? = 0,
    @SerializedName("temp")
    var temp: Temp? = Temp(),
    @SerializedName("pressure")
    var pressure: Double? = 0.0,
    @SerializedName("humidity")
    var humidity: Int? = 0,
    @SerializedName("weather")
    var weather: List<Weather?>? = listOf(),
    @SerializedName("speed")
    var speed: Double? = 0.0,
    @SerializedName("deg")
    var deg: Int? = 0,
    @SerializedName("clouds")
    var clouds: Int? = 0,
    @SerializedName("snow")
    var snow: Double? = 0.0
)

data class Temp(
    @SerializedName("day")
    var day: Double? = 0.0,
    @SerializedName("min")
    var min: Double? = 0.0,
    @SerializedName("max")
    var max: Double? = 0.0,
    @SerializedName("night")
    var night: Double? = 0.0,
    @SerializedName("eve")
    var eve: Double? = 0.0,
    @SerializedName("morn")
    var morn: Double? = 0.0
)


