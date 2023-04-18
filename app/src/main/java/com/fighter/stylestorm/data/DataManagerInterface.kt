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
    fun saveLocation(lat: Double, long: Double)
    fun saveLocationByCityName(location: String)
    fun getLocationByCityName(): String?
    fun getLatitude(): Double
    fun getLongitude(): Double
    fun getWeatherData(
        location: String?,
        onSuccessCallback: (response: WeatherResponse) -> Unit,
        onFailureCallback: (error: Throwable) -> Unit
    )
}