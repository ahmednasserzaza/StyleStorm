package com.fighter.stylestorm.ui.laundry

import com.fighter.stylestorm.databinding.FragmentLaundryBinding
import com.fighter.stylestorm.ui.base.BaseFragment

class LaundryFragment : BaseFragment<FragmentLaundryBinding>() {
    override val TAG: String = this::class.java.simpleName

    override fun getViewBinding(): FragmentLaundryBinding {
        return FragmentLaundryBinding.inflate(layoutInflater)
    }

    override fun setUp() {

    }

}