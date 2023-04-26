package com.fighter.stylestorm.data

import com.fighter.stylestorm.data.models.WeatherResponse

interface DataManagerInterface {
    fun getRandomWinterClothes(): Int?
    fun getRandomAutumnClothes(): Int?
    fun getRandomSummerClothes(): Int?
    fun getRandomSpringClothes(): Int?
    fun getClothesStoredInSharedPref(): List<Int>?
    fun addWearedClothesToPreferences(wearedItem: Int)
    fun clearAllClothesFromPreferences()
    fun saveLocationByCityName(location: String)
    fun getLocationByCityName(): String?
    fun getWeatherByCityName(
        location: String?,
        onSuccess: (response: WeatherResponse) -> Unit,
        onFailure: (error: Throwable) -> Unit
    )
    fun getWeatherByLatAndLong(
        latitude:Double,
        longitude:Double,
        onSuccess: (response: WeatherResponse) -> Unit,
        onFailure: (error: Throwable) -> Unit
    )
    fun setTheme(theme:Int)
    fun getTheme():Int
}