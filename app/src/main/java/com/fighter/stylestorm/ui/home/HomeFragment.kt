package com.fighter.stylestorm.ui.home

import android.view.View
import com.bumptech.glide.Glide
import com.fighter.stylestorm.R
import com.fighter.stylestorm.data.DataManager
import com.fighter.stylestorm.data.DataManagerInterface
import com.fighter.stylestorm.data.models.WeatherResponse
import com.fighter.stylestorm.databinding.FragmentHomeBinding
import com.fighter.stylestorm.ui.base.BaseFragment
import com.fighter.stylestorm.utils.SharedPreferences

class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    private val sharedPreferences by lazy { SharedPreferences(requireContext()) }
    private val dataManager: DataManagerInterface by lazy { DataManager(sharedPreferences) }

    override val TAG: String = this::class.java.simpleName
    override fun getViewBinding(): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(layoutInflater)
    }

    override fun setUp() {
        fetchWeatherData()
    }

    private fun fetchWeatherData() {
        val location = dataManager.getLocationByCityName()
        dataManager.getWeatherData(location, ::onSuccess, ::onFailure)
    }

    private fun onSuccess(weatherResponse: WeatherResponse) {
        hideNetworkPlaceHolder()
        initWeather(weatherResponse)
        setRandomImageBasedOnClimate(getRandomImageBasedOnClimate(weatherResponse))
    }

    private fun onFailure(error: Throwable) {
        log("Error:  ${error.message}")
        showNetworkPlaceHolder()
    }

    private fun showNetworkPlaceHolder() {
        activity?.runOnUiThread {
            binding.placeholderNetworkError.visibility = View.VISIBLE
            binding.textError.visibility = View.VISIBLE
            binding.weatherContainer.visibility = View.INVISIBLE
            binding.textSuggestionTitle.visibility = View.INVISIBLE
            binding.imageSuggestedItem.visibility = View.INVISIBLE
        }
    }

    private fun hideNetworkPlaceHolder() {
        activity?.runOnUiThread {
            binding.placeholderNetworkError.visibility = View.INVISIBLE
            binding.textError.visibility = View.INVISIBLE
            binding.weatherContainer.visibility = View.VISIBLE
            binding.textSuggestionTitle.visibility = View.VISIBLE
            binding.imageSuggestedItem.visibility = View.VISIBLE
        }
    }

    private fun showEmptyPlaceHolder() {
        binding.placeholderEmpty.visibility = View.VISIBLE
        binding.textEmpty.visibility = View.VISIBLE
        binding.textSuggestionTitle.visibility = View.INVISIBLE
    }

    private fun setRandomImageBasedOnClimate(randomItem: Int?) {
        activity?.runOnUiThread {
            if (randomItem != null) {
                Glide.with(binding.root)
                    .load(randomItem)
                    .into(binding.imageSuggestedItem)
                dataManager.addWearedClothesToPreferences(randomItem)
            } else {
                showEmptyPlaceHolder()
            }
        }
    }

    private fun getRandomImageBasedOnClimate(weatherResponse: WeatherResponse): Int? {
        val climateDegree = weatherResponse.current?.tempC
        return climateDegree?.let {
            when {
                it < 10 -> dataManager.getRandomWinterClothes()
                it in 10.0..20.0 -> dataManager.getRandomAutumnClothes()
                it > 20 && it <= 35 -> dataManager.getRandomSpringClothes()
                else -> dataManager.getRandomSummerClothes()
            }
        }
    }

    private fun initWeather(weatherResponse: WeatherResponse) {
        activity?.runOnUiThread {
            binding.apply {
                textWeatherDegree.text = "${weatherResponse.current?.tempC?.toInt()}° C"
                textCityName.text = "${weatherResponse.location?.name} , "
                textCountryName.text = weatherResponse.location?.country
                textWeatherState.text = weatherResponse.current?.condition?.text
                Glide.with(imageWeatherState)
                    .load("https:${weatherResponse.current?.condition?.icon}")
                    .placeholder(R.drawable.ic_placeholder)
                    .error(R.drawable.ic_error)
                    .into(imageWeatherState)
            }
        }
    }

}