package com.weather.mausamkikhabar.data.model

import com.google.gson.annotations.SerializedName

data class Clouds(
    @SerializedName("all")
    var all: Int? = 0
)