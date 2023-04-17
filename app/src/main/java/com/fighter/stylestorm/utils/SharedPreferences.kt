package com.fighter.stylestorm.utils

import android.content.Context

class SharedPreferences(context: Context) {

    private val sharedPreferences =
        context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)

    fun getImageList(): List<Int>? {
        val imageSet = sharedPreferences.getStringSet(
            IMAGE_LIST,
            null
        ) // use null as default value for nullable type
        return imageSet?.map { it.toInt() } // use safe-call operator to handle null list
    }

    fun setImageList(imageList: List<Int?>) { // use nullable type for parameter
        val editor = sharedPreferences.edit()
        val imageSet =
            imageList.map { it.toString() }.toSet() // use safe-call operator to handle null list
        editor.putStringSet(IMAGE_LIST, imageSet)
        editor.apply()
    }

    fun clearImageList() {
        val editor = sharedPreferences.edit()
        editor.remove(IMAGE_LIST)
        editor.apply()
    }

    var latitude: Float
        get() = sharedPreferences.getFloat("latitude", 0f)
        set(value) = sharedPreferences.edit().putFloat("latitude", value).apply()

    var longitude: Float
        get() = sharedPreferences.getFloat("longitude", 0f)
        set(value) = sharedPreferences.edit().putFloat("longitude", value).apply()

    companion object {
        private const val SHARED_PREFERENCES_NAME = "ImageSharedPreferences"
        private const val IMAGE_LIST = "ImageList"
    }

}
