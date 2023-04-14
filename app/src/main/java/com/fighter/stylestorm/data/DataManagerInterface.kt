package com.fighter.stylestorm.data

interface DataManagerInterface {
    fun getRandomSummerItem():Int
    fun getRandomWinterItem():Int
    fun getClothesStoredInSharedPref():List<Int>?
}