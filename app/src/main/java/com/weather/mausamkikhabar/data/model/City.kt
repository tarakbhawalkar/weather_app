package com.weather.mausamkikhabar.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Created on 19-Jul-19.
 */
@Parcelize
data class City(
    @SerializedName("id")
    var id: Int? = 0,
    @SerializedName("geoname_id")
    var geonameId: Int? = 0,
    @SerializedName("name")
    var name: String? = "",
    @SerializedName("lat")
    var lat: Double? = 0.0,
    @SerializedName("lon")
    var lon: Double? = 0.0,
    @SerializedName("country")
    var country: String? = "",
    @SerializedName("iso2")
    var iso2: String? = "",
    @SerializedName("type")
    var type: String? = "",
    @SerializedName("population")
    var population: Int? = 0
) : Parcelable