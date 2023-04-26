package com.fighter.stylestorm.data

import com.fighter.stylestorm.BuildConfig
import com.fighter.stylestorm.R
import com.fighter.stylestorm.data.models.WeatherResponse
import com.fighter.stylestorm.utils.Constants
import com.fighter.stylestorm.utils.SharedPreferences
import com.fighter.stylestorm.utils.executeWithCallbacks
import com.google.gson.reflect.TypeToken
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor

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

    private val logInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BASIC
    }

    private val client = OkHttpClient.Builder().apply {
        addInterceptor(logInterceptor)
    }.build()

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

    override fun saveLocationByCityName(location: String) {
        sharedPref.location = location
    }

    override fun getLocationByCityName(): String? {
        return sharedPref.location
    }

    override fun getWeatherByCityName(
        location: String?,
        onSuccess: (response: WeatherResponse) -> Unit,
        onFailure: (error: Throwable) -> Unit
    ) {
        val formBody = FormBody.Builder()
            .add("key" , BuildConfig.API_KEY)
            .add("q" , "$location")
            .build()

        val request = Request.Builder()
            .url(Constants.URL)
            .post(formBody)
            .build()

        val responseType = object : TypeToken<WeatherResponse>() {}.type

        client.executeWithCallbacks(
            request,
            responseType,
            onSuccess,
            onFailure
        )
    }

    override fun getWeatherByLatAndLong(
        latitude: Double,
        longitude: Double,
        onSuccess: (response: WeatherResponse) -> Unit,
        onFailure: (error: Throwable) -> Unit
    ) {
        val formBody = FormBody.Builder()
            .add("key", BuildConfig.API_KEY)
            .add("q", "$latitude,$longitude")
            .build()

        val request = Request.Builder()
            .url(Constants.URL)
            .post(formBody)
            .build()

        val responseType = object : TypeToken<WeatherResponse>() {}.type

        client.executeWithCallbacks(
            request,
            responseType,
            onSuccess,
            onFailure
        )
    }

    override fun setTheme(theme: Int) {
        sharedPref.setTheme(theme)
    }

    override fun getTheme(): Int {
        return sharedPref.getTheme()
    }
}