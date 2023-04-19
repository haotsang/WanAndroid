package com.haotsang.wanandroid.ui.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.forEachIndexed
import com.haotsang.wanandroid.base.BaseViewBindingFragment
import com.haotsang.wanandroid.databinding.ProfileFragmentBinding

class ProfileFragment : BaseViewBindingFragment<ProfileFragmentBinding>() {
    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): ProfileFragmentBinding {
        return ProfileFragmentBinding.inflate(inflater, container, false)
    }

    override fun observe() {

    }

    override fun initView() {
        for (index in 0 until binding?.itemContainer!!.childCount) {
            val child = binding?.itemContainer!!.getChildAt(index)
            child.setOnClickListener {  }
        }
    }

    override fun initData() {

    }
}