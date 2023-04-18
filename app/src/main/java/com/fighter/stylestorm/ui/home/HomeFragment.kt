package com.fighter.stylestorm.ui.home

import android.annotation.SuppressLint
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
        showLoading()
        fetchWeatherData()
    }

    private fun fetchWeatherData() {
        val location = dataManager.getLocationByCityName()
        dataManager.getWeatherData(location, ::onSuccess, ::onFailure)
    }

    private fun onSuccess(weatherResponse: WeatherResponse) {
        hideLoading()
        hideNetworkPlaceHolder()
        initWeather(weatherResponse)
        setRandomImageBasedOnClimate(getRandomImageBasedOnClimate(weatherResponse))
    }

    private fun onFailure(error: Throwable) {
        log("Error:  ${error.message}")
        hideLoading()
        showNetworkPlaceHolder()
    }

    private fun showNetworkPlaceHolder() {
        activity?.runOnUiThread {
            with(binding) {
                placeholderNetworkError.visibility = View.VISIBLE
                textError.visibility = View.VISIBLE
                weatherContainer.visibility = View.INVISIBLE
                statusContainer.visibility = View.INVISIBLE
                textSuggestionTitle.visibility = View.INVISIBLE
                imageSuggestedItem.visibility = View.INVISIBLE
            }
        }
    }

    private fun hideNetworkPlaceHolder() {
        activity?.runOnUiThread {
            with(binding) {
                placeholderNetworkError.visibility = View.INVISIBLE
                textError.visibility = View.INVISIBLE
                weatherContainer.visibility = View.VISIBLE
                statusContainer.visibility = View.VISIBLE
                textSuggestionTitle.visibility = View.VISIBLE
                imageSuggestedItem.visibility = View.VISIBLE
            }
        }
    }

    private fun showEmptyPlaceHolder() {
        with(binding) {
            placeholderEmpty.visibility = View.VISIBLE
            textEmpty.visibility = View.VISIBLE
            textSuggestionTitle.visibility = View.INVISIBLE
        }

    }

    private fun showLoading() {
        with(binding) {
            lottieLoading.visibility = View.VISIBLE
            weatherContainer.visibility = View.GONE
            statusContainer.visibility = View.GONE
            imageSuggestedItem.visibility = View.GONE
            textSuggestionTitle.visibility = View.GONE
        }
    }

    private fun hideLoading() {
        activity?.runOnUiThread {
            with(binding) {
                lottieLoading.visibility = View.GONE
                weatherContainer.visibility = View.VISIBLE
                statusContainer.visibility = View.VISIBLE
                imageSuggestedItem.visibility = View.VISIBLE
                textSuggestionTitle.visibility = View.VISIBLE
            }
        }
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
        val climateDegree = weatherResponse.current?.tempC?.toInt()
        return climateDegree?.let {
            when {
                it < 10 -> dataManager.getRandomWinterClothes()
                it in 10..20 -> dataManager.getRandomAutumnClothes()
                it in 21..30 -> dataManager.getRandomSpringClothes()
                else -> dataManager.getRandomSummerClothes()
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun initWeather(weatherResponse: WeatherResponse) {
        activity?.runOnUiThread {
            with(binding) {
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