package com.fighter.stylestorm.data.models


import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    @SerializedName("current") val current: Current? = null,
    @SerializedName("location") val location: Location? = null
)