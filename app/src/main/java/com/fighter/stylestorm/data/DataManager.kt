package com.fighter.stylestorm.data

import com.fighter.stylestorm.R
import com.fighter.stylestorm.utils.SharedPreferences

class DataManager(private val sharedPref: SharedPreferences) : DataManagerInterface {

    private val winterClothes = listOf(
        R.drawable.collection10,
        R.drawable.collection11,
        R.drawable.collection12,
        R.drawable.collection13,
        R.drawable.collection14,
        R.drawable.collection15,
    )
    private val autumnClothes = listOf(
        R.drawable.collection00,
        R.drawable.collection01,
        R.drawable.collection02,
        R.drawable.collection03,
        R.drawable.collection04,
        R.drawable.collection05,
    )
    private val springClothes = listOf(
        R.drawable.collection20,
        R.drawable.collection21,
        R.drawable.collection22,
        R.drawable.collection23,
        R.drawable.collection24,
        R.drawable.collection25,
    )
    private val summerClothes = listOf(
        R.drawable.collection30,
        R.drawable.collection31,
        R.drawable.collection32,
        R.drawable.collection33,
        R.drawable.collection34,
        R.drawable.collection35,
        R.drawable.collection36,
    )

    override fun getRandomWinterClothes(): Int? {
        val winterClothes = winterClothes.toList()
        val imageList = sharedPref.getImageList() ?: emptyList()
        if (imageList.containsAll(winterClothes)) {
            return null
        }

        var randomWinterItem: Int
        do {
            randomWinterItem = winterClothes.random()
        } while (randomWinterItem in imageList)

        return randomWinterItem
    }

    override fun getRandomAutumnClothes(): Int? {
        val autumnClothes = autumnClothes.toList()
        val imageList = sharedPref.getImageList() ?: emptyList()
        if (imageList.containsAll(autumnClothes)) {
            return null
        }

        var randomAutumnItem: Int
        do {
            randomAutumnItem = autumnClothes.random()
        } while (randomAutumnItem in imageList)

        return randomAutumnItem
    }

    override fun getRandomSummerClothes(): Int? {
        val summerClothes = summerClothes.toList()
        val imageList = sharedPref.getImageList() ?: emptyList()
        if (imageList.containsAll(summerClothes)) {
            return null
        }

        var randomSummerItem: Int
        do {
            randomSummerItem = summerClothes.random()
        } while (randomSummerItem in imageList)

        return randomSummerItem
    }

    override fun getRandomSpringClothes(): Int? {
        val springClothes = springClothes.toList()
        val imageList = sharedPref.getImageList() ?: emptyList()
        if (imageList.containsAll(springClothes)) {
            return null
        }

        var randomSpringItem: Int
        do {
            randomSpringItem = springClothes.random()
        } while (randomSpringItem in imageList)

        return randomSpringItem
    }


    override fun addWearedClothesToPreferences(wearedItem: Int) {
        sharedPref.getImageList()?.let { imageList ->
            sharedPref.setImageList(imageList.toMutableList().apply { add(wearedItem) })
        } ?: sharedPref.setImageList(listOf(wearedItem))
    }

    override fun getClothesStoredInSharedPref(): List<Int> {
        return sharedPref.getImageList()?.takeIf { it.isNotEmpty() } ?: emptyList()
    }

    override fun clearAllClothesFromPreferences() {
        sharedPref.clearImageList()
    }

    override fun saveLocation(lat: Double, long: Double) {
        sharedPref.latitude = lat.toFloat()
        sharedPref.longitude = lat.toFloat()
    }

    override fun getLatitude(): Double {
        return sharedPref.latitude.toDouble()
    }

    override fun getLongitude(): Double {
        return sharedPref.longitude.toDouble()
    }
}