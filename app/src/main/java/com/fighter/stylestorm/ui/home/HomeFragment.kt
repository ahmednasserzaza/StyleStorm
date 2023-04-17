package com.fighter.stylestorm.ui.home

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.widget.Toast
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

class HomeFragment : BaseFragment<FragmentHomeBinding>(), WeatherCallback {
    private val sharedPreferences by lazy { SharedPreferences(requireContext()) }
    private val dataManager: DataManagerInterface by lazy { DataManager(sharedPreferences) }

    override val TAG: String = this::class.java.simpleName
    override fun getViewBinding(): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(layoutInflater)
    }

    override fun setUp() {
        requestPermissionFromUser()
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
                    dataManager.saveLocation(latitude , longitude)
                    Network.makeRequestUsingOkhttp(this, longitude, latitude)
                }
            }
            .addOnFailureListener { e ->
                // Handle the error
            }
    }


    private fun fetchWeatherData() =
        Network.makeRequestUsingOkhttp(this, dataManager.getLatitude(), dataManager.getLongitude())

    override fun onSuccess(weatherResponse: WeatherResponse) {
        initWeather(weatherResponse)
        setRandomImageBasedOnClimate(getRandomImageBasedOnClimate(weatherResponse))
    }

    override fun onError(message: String) {
        log("Error:  $message")
    }

    private fun setRandomImageBasedOnClimate(randomItem: Int?) {
        activity?.runOnUiThread {
            Glide.with(binding.root)
                .load(randomItem)
                .into(binding.imageSuggestedItem)
            dataManager.addWearedClothesToPreferences(randomItem!!)
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

    @SuppressLint("SetTextI18n")
    private fun initWeather(weatherResponse: WeatherResponse) {
        activity?.runOnUiThread {
            Toast.makeText(
                requireContext(),
                "Success : ${weatherResponse.location?.country}",
                Toast.LENGTH_LONG
            ).show()
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

    companion object{
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1000
    }

}