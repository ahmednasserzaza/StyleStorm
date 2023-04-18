package com.fighter.stylestorm.utils

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate

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

    var location: String?
        get() = sharedPreferences.getString(LOCATION, "Cairo")
        set(value) = sharedPreferences.edit().putString(LOCATION, value).apply()

    fun getTheme(): Int {
        return sharedPreferences.getInt(THEME, AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
    }

    fun setTheme(theme: Int) {
        val editor = sharedPreferences.edit()
        editor.putInt(THEME, theme)
        editor.apply()
    }

    companion object {
        private const val SHARED_PREFERENCES_NAME = "ImageSharedPreferences"
        private const val IMAGE_LIST = "ImageList"
        private const val LOCATION = "location"
        private const val THEME = "theme"
    }

}
