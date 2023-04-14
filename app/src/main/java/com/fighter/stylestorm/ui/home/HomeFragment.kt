package com.fighter.stylestorm.ui.home

import android.widget.Toast
import com.fighter.stylestorm.data.DataManager
import com.fighter.stylestorm.data.DataManagerInterface
import com.fighter.stylestorm.data.Network
import com.fighter.stylestorm.data.WeatherCallback
import com.fighter.stylestorm.data.models.WeatherResponse
import com.fighter.stylestorm.databinding.FragmentHomeBinding
import com.fighter.stylestorm.ui.base.BaseFragment
import com.fighter.stylestorm.utils.SharedPreferences

class HomeFragment : BaseFragment<FragmentHomeBinding>() , WeatherCallback {
    private val sharedPreferences by lazy { SharedPreferences(requireContext()) }
    private val dataManager: DataManagerInterface by lazy { DataManager(sharedPreferences) }

    override val TAG: String = this::class.java.simpleName
    override fun getViewBinding(): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(layoutInflater)
    }

    override fun setUp() {
        fetchWeatherData()
    }
    private fun fetchWeatherData() = Network.makeRequestUsingOkhttp(this , lat = 57.358 , long = 8785.57)
    override fun onSuccess(weatherResponse: WeatherResponse) {
       initWeather(weatherResponse)
    }

    override fun onError(message: String) {
        log("Error:  $message")
    }

    private fun initWeather(weatherResponse: WeatherResponse){
        activity?.runOnUiThread {
            Toast.makeText(requireContext() , "Success : ${weatherResponse.location?.country}" , Toast.LENGTH_LONG).show()
            binding.apply {
                textWeatherDegree.text = weatherResponse.current?.tempC.toString()
                textCityName.text = weatherResponse.location?.region
                textCountryName.text = weatherResponse.location?.country
                textWeatherState.text = weatherResponse.current?.condition?.text
            }
        }
    }

}