package com.weather.mausamkikhabar.data.model

import com.google.gson.annotations.SerializedName

data class Wind(
    @SerializedName("speed")
    var speed: Double? = 0.0,
    @SerializedName("deg")
    var deg: Int? = 0
)