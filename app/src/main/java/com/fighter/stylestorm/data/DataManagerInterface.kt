package com.fighter.stylestorm.data

interface DataManagerInterface {
    fun getRandomWinterClothes(): Int?
    fun getRandomAutumnClothes(): Int?
    fun getRandomSummerClothes(): Int?
    fun getRandomSpringClothes(): Int?
    fun getClothesStoredInSharedPref(): List<Int>?
    fun addWearedClothesToPreferences(wearedItem: Int)
    fun clearAllClothesFromPreferences()
    fun saveLocation(lat: Double, long: Double)
    fun getLatitude(): Double
    fun getLongitude(): Double
}