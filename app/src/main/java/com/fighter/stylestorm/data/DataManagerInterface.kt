package com.fighter.stylestorm.data

interface DataManagerInterface {
    fun getRandomSummerItem():Int?
    fun getRandomWinterItem():Int
    fun getClothesStoredInSharedPref():List<Int>?
    fun addWearedClothesToPreferences(wearedItem:Int)
    fun clearAllClothesFromPreferences()
    fun saveLocation(lat:Double , long:Double)
    fun getLatitude():Double
    fun getLongitude():Double
}