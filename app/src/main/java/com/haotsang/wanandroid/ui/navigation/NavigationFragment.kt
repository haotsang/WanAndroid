package com.haotsang.wanandroid.ui.navigation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.haotsang.wanandroid.base.BaseViewBindingFragment
import com.haotsang.wanandroid.databinding.NavigationFragmentBinding

class NavigationFragment : BaseViewBindingFragment<NavigationFragmentBinding>() {

    private val viewModel : NavigationViewModel by viewModels()
    private lateinit var adapter: NavigationAdapter

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): NavigationFragmentBinding {
        return NavigationFragmentBinding.inflate(inflater, container, false)
    }

    override fun observe() {
        viewModel.getNavigationObserve().observe(this) {
            adapter.setList(it)
        }
    }

    override fun initView() {
        val flexboxLayoutManager = com.google.android.flexbox.FlexboxLayoutManager(requireContext())
        flexboxLayoutManager.setFlexDirection(com.google.android.flexbox.FlexDirection.ROW)
        flexboxLayoutManager.setFlexWrap(com.google.android.flexbox.FlexWrap.WRAP)
        flexboxLayoutManager.setJustifyContent(com.google.android.flexbox.JustifyContent.FLEX_START)
        flexboxLayoutManager.setAlignItems(com.google.android.flexbox.AlignItems.STRETCH)

        adapter = NavigationAdapter()
        binding?.recyclerview?.layoutManager = flexboxLayoutManager
        binding?.recyclerview?.adapter = adapter
    }

    override fun initData() {
        viewModel.getNavigationData()
    }
}