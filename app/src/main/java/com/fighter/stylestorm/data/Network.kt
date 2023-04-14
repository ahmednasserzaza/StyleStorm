package com.fighter.stylestorm.data

import com.fighter.stylestorm.data.models.WeatherResponse
import com.fighter.stylestorm.utils.Constants
import com.google.gson.Gson
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import java.io.IOException

object Network {
    private val logInterceptor = HttpLoggingInterceptor()
    private val client = OkHttpClient().newBuilder()
        .addInterceptor(
            logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        )
        .build()
    private val gson = Gson()

    fun makeRequestUsingOkhttp(callback: WeatherCallback, lat: Double, long: Double) {

        val request = Request.Builder()
            .url(Constants.WEATHER_LINK + "&q=$lat,$long")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                callback.onError(e.message ?: "Unknown error")
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val responseString = response.body?.string()
                    val weatherResponse = gson.fromJson(responseString, WeatherResponse::class.java)
                    callback.onSuccess(weatherResponse)
                } else {
                    callback.onError(response.message)
                }
            }
        })
    }
}