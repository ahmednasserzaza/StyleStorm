package com.fighter.stylestorm.data

import com.fighter.stylestorm.R
import com.fighter.stylestorm.utils.SharedPreferences

class DataManager(private val sharedPref:SharedPreferences) : DataManagerInterface {
    private val summerClothes = listOf(
        R.drawable.summer,
        R.drawable.summer1,
        R.drawable.summer2,
        R.drawable.summer3,
        R.drawable.summer4,
        R.drawable.summer5,
        R.drawable.summer6
    )
    private val winterClothes = listOf(
        R.drawable.winter1,
        R.drawable.winter2,
        R.drawable.winter3,
        R.drawable.winter4,
        R.drawable.winter5,
        R.drawable.winter6
    )

    override fun getRandomSummerItem(): Int {
        var randomSummerClothes = summerClothes.random()
        while (randomSummerClothes in (sharedPref.getImageList() ?: emptyList())) {
            randomSummerClothes = summerClothes.random()
        }
        return randomSummerClothes
    }
    override fun addWearedClothesToPreferences(wearedItem: Int) {
        sharedPref.getImageList()?.let { imageList ->
            sharedPref.setImageList(imageList.toMutableList().apply { add(wearedItem) })
        } ?: sharedPref.setImageList(listOf(wearedItem))
    }
    override fun getRandomWinterItem(): Int {
        var randomWinterClothes = winterClothes.random()
        while (randomWinterClothes in (sharedPref.getImageList() ?: emptyList())) {
            randomWinterClothes = winterClothes.random()
        }
        return randomWinterClothes
    }

    override fun getClothesStoredInSharedPref(): List<Int>? {
        return if (sharedPref.getImageList() != null){
            sharedPref.getImageList()
        }else{
            emptyList()
        }
    }
}