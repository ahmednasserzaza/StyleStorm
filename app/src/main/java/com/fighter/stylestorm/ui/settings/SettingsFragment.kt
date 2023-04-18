package com.fighter.stylestorm.ui.settings

import android.widget.Toast
import com.fighter.stylestorm.data.DataManager
import com.fighter.stylestorm.data.DataManagerInterface
import com.fighter.stylestorm.databinding.FragmentSettingsBinding
import com.fighter.stylestorm.ui.base.BaseFragment
import com.fighter.stylestorm.utils.SharedPreferences

class SettingsFragment : BaseFragment<FragmentSettingsBinding>(){
    private val sharedPreferences by lazy { SharedPreferences(requireContext()) }
    private val dataManager: DataManagerInterface by lazy { DataManager(sharedPreferences) }

    override val TAG: String = this::class.java.simpleName

    override fun getViewBinding(): FragmentSettingsBinding {
        return FragmentSettingsBinding.inflate(layoutInflater)
    }

    override fun setUp() {
        addCallBacks()
        setTextLocation()
    }
    private fun addCallBacks() {
        binding.buttonUpdateLocation.setOnClickListener {
            updateLocation()
        }
    }

    private fun updateLocation() {
        val location = binding.etCityName.text.toString()
        if (location.isNotEmpty()){
            dataManager.saveLocationByCityName(location)
            setTextLocation()
        }else{
            Toast.makeText(requireContext() , "Location can't be empty" , Toast.LENGTH_LONG).show()
        }
    }

    private fun setTextLocation() {
        val currentLocation = dataManager.getLocationByCityName()
        binding.textCurrentLocation.text = currentLocation
    }

}