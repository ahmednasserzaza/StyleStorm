package com.fighter.stylestorm.data

import com.fighter.stylestorm.data.models.WeatherResponse

interface WeatherCallback {
    fun onSuccess(weatherResponse: WeatherResponse)
    fun onError(message: String)
}