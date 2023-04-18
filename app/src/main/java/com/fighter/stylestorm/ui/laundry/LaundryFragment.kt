package com.fighter.stylestorm.ui.laundry

import android.os.Handler
import android.view.View
import com.fighter.stylestorm.data.DataManager
import com.fighter.stylestorm.data.DataManagerInterface
import com.fighter.stylestorm.databinding.FragmentLaundryBinding
import com.fighter.stylestorm.ui.base.BaseFragment
import com.fighter.stylestorm.utils.SharedPreferences

class LaundryFragment : BaseFragment<FragmentLaundryBinding>() {
    private val sharedPreferences by lazy { SharedPreferences(requireContext()) }
    private val dataManager: DataManagerInterface by lazy { DataManager(sharedPreferences) }
    private lateinit var adapter: LaundryAdapter

    override val TAG: String = this::class.java.simpleName

    override fun getViewBinding(): FragmentLaundryBinding {
        return FragmentLaundryBinding.inflate(layoutInflater)
    }

    override fun setUp() {
        setupAdapter()
        addCallBacks()
    }

    private fun addCallBacks() {
        binding.fabCleanItems.setOnClickListener {
            Handler().postDelayed({
                // show lottie
                showClothesCleaned()
            }, 3000)
            washClothes()
        }
    }

    private fun showClothesCleaned() {
        binding.textAllCleaned.visibility = View.VISIBLE
        binding.imageAllCleaned.visibility = View.VISIBLE
        binding.placeholderWashing.visibility = View.INVISIBLE
        binding.textWashing.visibility = View.INVISIBLE
        binding.fabCleanItems.visibility = View.INVISIBLE
    }

    private fun showWashingClothes() {
        binding.placeholderWashing.visibility = View.VISIBLE
        binding.textWashing.visibility = View.VISIBLE
        binding.fabCleanItems.visibility = View.INVISIBLE
        binding.textAllCleaned.visibility = View.INVISIBLE
        binding.imageAllCleaned.visibility = View.INVISIBLE
    }


    private fun washClothes() {
        dataManager.clearAllClothesFromPreferences()
        val clothesAfterClear = dataManager.getClothesStoredInSharedPref()
        adapter.setData(clothesAfterClear!!)
        showWashingClothes()
    }

    private fun setupAdapter() {
        val dirtyClothes = dataManager.getClothesStoredInSharedPref()
        adapter = LaundryAdapter(dirtyClothes!!)
        binding.recyclerLaundry.adapter = adapter
    }

}