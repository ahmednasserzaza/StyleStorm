package com.fighter.stylestorm.ui.settings

import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import com.fighter.stylestorm.R
import com.fighter.stylestorm.data.DataManager
import com.fighter.stylestorm.data.DataManagerInterface
import com.fighter.stylestorm.databinding.FragmentSettingsBinding
import com.fighter.stylestorm.ui.base.BaseFragment
import com.fighter.stylestorm.utils.SharedPreferences

class SettingsFragment : BaseFragment<FragmentSettingsBinding>() {
    private val sharedPreferences by lazy { SharedPreferences(requireContext()) }
    private val dataManager: DataManagerInterface by lazy { DataManager(sharedPreferences) }

    override val TAG: String = this::class.java.simpleName
    override fun getViewBinding(): FragmentSettingsBinding {
        return FragmentSettingsBinding.inflate(layoutInflater)
    }

    override fun setUp() {
        setUpDefaultTheme()
        onSwitchTheme()
        addCallBacks()
        setTextLocation()
    }

    private fun setUpDefaultTheme() {
        val currentTheme = dataManager.getTheme()
        binding.switchTheme.isChecked = currentTheme != AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
    }

    private fun addCallBacks() {
        binding.buttonUpdateLocation.setOnClickListener {
            updateLocation()
        }
    }

    private fun onSwitchTheme() {
        binding.switchTheme.setOnCheckedChangeListener { _, isChecked ->
            val newTheme = if (isChecked) {
                AppCompatDelegate.MODE_NIGHT_YES
            } else {
                AppCompatDelegate.MODE_NIGHT_NO
            }
            AppCompatDelegate.setDefaultNightMode(newTheme)
        }
    }


    private fun updateLocation() {
        val location = binding.etCityName.text.toString()
        if (location.isNotEmpty()) {
            dataManager.saveLocationByCityName(location)
            setTextLocation()
            binding.etCityName.setText("")
            val inputMethodManager =
                activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(binding.etCityName.windowToken, 0)
            Toast.makeText(requireContext(), getString(R.string.updatedLocation), Toast.LENGTH_LONG)
                .show()
        } else {
            Toast.makeText(requireContext(), getString(R.string.emptyValidation), Toast.LENGTH_LONG)
                .show()
        }
    }

    private fun setTextLocation() {
        val currentLocation = dataManager.getLocationByCityName()
        binding.textCurrentLocation.text = currentLocation
    }

}