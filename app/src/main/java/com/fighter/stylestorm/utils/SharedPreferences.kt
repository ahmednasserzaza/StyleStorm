package com.fighter.stylestorm.utils

import android.content.Context

class SharedPreferences(context: Context) {

    private val sharedPreferences =
        context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)

    fun getImageList(): List<Int>? {
        val imageSet = sharedPreferences.getStringSet(
            IMAGE_LIST,
            null
        )
        return imageSet?.map { it.toInt() }
    }

    fun setImageList(imageList: List<Int?>) {
        val editor = sharedPreferences.edit()
        val imageSet =
            imageList.map { it.toString() }.toSet()
        editor.putStringSet(IMAGE_LIST, imageSet)
        editor.apply()
    }

    fun clearImageList() {
        val editor = sharedPreferences.edit()
        editor.remove(IMAGE_LIST)
        editor.apply()
    }

    var latitude: Float
        get() = sharedPreferences.getFloat(LATITUDE, LATITUDE_DEFAULT_VALUE)
        set(value) = sharedPreferences.edit().putFloat(LATITUDE, value).apply()

    var longitude: Float
        get() = sharedPreferences.getFloat(LONGITUDE, LONGITUDE_DEFAULT_VALUE)
        set(value) = sharedPreferences.edit().putFloat(LONGITUDE, value).apply()

    var location: String?
        get() = sharedPreferences.getString(LOCATION, "Cairo")
        set(value) = sharedPreferences.edit().putString(LOCATION, value).apply()

    companion object {
        private const val SHARED_PREFERENCES_NAME = "ImageSharedPreferences"
        private const val IMAGE_LIST = "ImageList"
        private const val LATITUDE = "latitude"
        private const val LONGITUDE = "longitude"
        private const val LOCATION = "location"
        private const val LATITUDE_DEFAULT_VALUE = 30.044420f
        private const val LONGITUDE_DEFAULT_VALUE = 31.235712f
    }

}
