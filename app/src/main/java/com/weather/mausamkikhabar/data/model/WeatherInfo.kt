package com.weather.mausamkikhabar.data.model
import com.google.gson.annotations.SerializedName


/**
 * Created on 22-Jul-19.
 */
data class WeatherInfo(
    @SerializedName("coord")
    var coord: Coord? = Coord(),
    @SerializedName("weather")
    var weather: List<Weather?>? = listOf(),
    @SerializedName("base")
    var base: String? = "",
    @SerializedName("main")
    var main: Main? = Main(),
    @SerializedName("visibility")
    var visibility: Int? = 0,
    @SerializedName("wind")
    var wind: Wind? = Wind(),
    @SerializedName("clouds")
    var clouds: Clouds? = Clouds(),
    @SerializedName("dt")
    var dt: Int? = 0,
    @SerializedName("sys")
    var sys: Sys? = Sys(),
    @SerializedName("timezone")
    var timezone: Int? = 0,
    @SerializedName("id")
    var id: Int? = 0,
    @SerializedName("name")
    var name: String? = "",
    @SerializedName("cod")
    var cod: Int? = 0
)








