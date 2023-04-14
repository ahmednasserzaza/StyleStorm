package com.fighter.stylestorm.utils

import com.fighter.stylestorm.BuildConfig

object Constants {
    private const val BASE_URL = "http://api.weatherapi.com/v1"
    private const val WEATHER_CURRENT_END_POINT = "/current.json"
    const val WEATHER_LINK = BASE_URL+ WEATHER_CURRENT_END_POINT+"?key=${BuildConfig.API_KEY}"
}