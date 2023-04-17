package com.fighter.stylestorm.ui.home

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.view.View
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.fighter.stylestorm.R
import com.fighter.stylestorm.data.DataManager
import com.fighter.stylestorm.data.DataManagerInterface
import com.fighter.stylestorm.data.Network
import com.fighter.stylestorm.data.WeatherCallback
import com.fighter.stylestorm.data.models.WeatherResponse
import com.fighter.stylestorm.databinding.FragmentHomeBinding
import com.fighter.stylestorm.ui.base.BaseFragment
import com.fighter.stylestorm.utils.SharedPreferences
import com.google.android.gms.location.LocationServices
import com.google.android.material.snackbar.Snackbar

class HomeFragment : BaseFragment<FragmentHomeBinding>(), WeatherCallback {
    private val sharedPreferences by lazy { SharedPreferences(requireContext()) }
    private val dataManager: DataManagerInterface by lazy { DataManager(sharedPreferences) }

    override val TAG: String = this::class.java.simpleName
    override fun getViewBinding(): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(layoutInflater)
    }

    override fun setUp() {
        requestPermissionFromUser()
        log("${sharedPreferences.longitude} , ${sharedPreferences.longitude}")
        fetchWeatherData()
    }

    private fun requestPermissionFromUser() {
        if (!checkLocationPermission()) {
            requestPermissions(
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
            return
        }
    }

    private fun checkLocationPermission(): Boolean {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return false
        }
        return true
    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted
                // Get the user's location
                getUserLocation()
            } else {
                // Permission denied
                // Handle the error
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun getUserLocation() {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                if (location != null) {
                    val latitude = location.latitude
                    val longitude = location.longitude
                    Network.makeRequestUsingOkhttp(this, longitude, latitude)
                    dataManager.saveLocation(latitude, longitude)
                }
            }
            .addOnFailureListener { e ->
                Snackbar.make(binding.root, e.message.toString(), Snackbar.LENGTH_SHORT).show()
            }
    }


    private fun fetchWeatherData() =
        Network.makeRequestUsingOkhttp(this, dataManager.getLatitude(), dataManager.getLongitude())

    override fun onSuccess(weatherResponse: WeatherResponse) {
        hideNetworkPlaceHolder()
        initWeather(weatherResponse)
        setRandomImageBasedOnClimate(getRandomImageBasedOnClimate(weatherResponse))
    }

    override fun onError(message: String) {
        log("Error:  $message")
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
            if (randomItem != null){
                Glide.with(binding.root)
                    .load(randomItem)
                    .into(binding.imageSuggestedItem)
                dataManager.addWearedClothesToPreferences(randomItem!!)
            }else{
                showEmptyPlaceHolder()
            }
        }
    }

    private fun getRandomImageBasedOnClimate(weatherResponse: WeatherResponse): Int? {
        val climateDegree = weatherResponse.current?.tempC
        return climateDegree?.let {
            if (it > 18) {
                dataManager.getRandomSummerItem()
            } else {
                dataManager.getRandomWinterItem()
            }
        }
    }

    private fun initWeather(weatherResponse: WeatherResponse) {
        activity?.runOnUiThread {
            binding.apply {
                textWeatherDegree.text = "${weatherResponse.current?.tempC}°C"
                textCityName.text = "${weatherResponse.location?.region} , "
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

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1000
    }

}