package com.fighter.stylestorm.ui.laundry

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
            washClothes()
        }
    }

    private fun washClothes() {
        dataManager.clearAllClothesFromPreferences()
        adapter.setData(dataManager.getClothesStoredInSharedPref()!!)
    }

    private fun setupAdapter() {
        val dirtyClothes = dataManager.getClothesStoredInSharedPref()
        adapter = LaundryAdapter(dirtyClothes!!)
        binding.recyclerLaundry.adapter = adapter
    }

}