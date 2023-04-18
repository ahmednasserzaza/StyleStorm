package com.fighter.stylestorm.utils

import com.google.gson.Gson
import okhttp3.*
import java.io.IOException
import java.lang.reflect.Type

fun <T> OkHttpClient.executeWithCallbacks(
    request: Request,
    responseType: Type,
    onSuccess: (response: T) -> Unit,
    onFailure: (error: Throwable) -> Unit
): Call {
    val call = newCall(request)
    val callback = object : Callback {
        override fun onResponse(call: Call, response: Response) {
            if (response.isSuccessful) {
                val responseBody = response.body?.string()
                val gson = Gson()
                val result = gson.fromJson<T>(responseBody, responseType)
                onSuccess(result)
            } else {
                onFailure(Throwable("$response"))
            }
        }

        override fun onFailure(call: Call, e: IOException) {
            onFailure(e)
        }
    }
    call.enqueue(callback)
    return call
}