package com.fighter.stylestorm.ui.settings

import com.fighter.stylestorm.databinding.FragmentSettingsBinding
import com.fighter.stylestorm.ui.base.BaseFragment

class SettingsFragment : BaseFragment<FragmentSettingsBinding>(){
    override val TAG: String = this::class.java.simpleName

    override fun getViewBinding(): FragmentSettingsBinding {
        return FragmentSettingsBinding.inflate(layoutInflater)
    }

    override fun setUp() {

    }

}