package com.haotsang.wanandroid.ui.settngs

import com.haotsang.wanandroid.base.BaseFragment
import com.haotsang.wanandroid.databinding.SettingFragmentBinding

class SettingFragment : BaseFragment<SettingFragmentBinding>(SettingFragmentBinding::inflate) {

    override fun initialize() {
        super.initialize()
        mBinding?.settingsToolbar?.apply {
            title = "Settings"
            setNavigationOnClickListener {
                requireActivity().supportFragmentManager.popBackStack()
            }
        }

        childFragmentManager.beginTransaction()
            .replace(mBinding?.settingsContainer!!.id, SettingsPreferenceFragment())
            .commitAllowingStateLoss()
    }
}