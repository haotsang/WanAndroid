package com.haotsang.wanandroid.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<VB : ViewBinding>(private val inflate: (LayoutInflater) -> VB) : Fragment() {

    private var binding: VB? = null
    protected open val mBinding get() = binding

    open fun initialize() {}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = inflate(layoutInflater)
        return mBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.isClickable = true // 防止view穿透

        initialize()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

}