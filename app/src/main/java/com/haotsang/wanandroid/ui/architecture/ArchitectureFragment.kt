package com.haotsang.wanandroid.ui.architecture

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.haotsang.wanandroid.base.BaseViewBindingFragment
import com.haotsang.wanandroid.databinding.ArchitectureFragmentBinding

class ArchitectureFragment : BaseViewBindingFragment<ArchitectureFragmentBinding>() {

    private val viewModel : ArchitectureViewModel by viewModels()
    private lateinit var adapter: ArchitectureAdapter

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): ArchitectureFragmentBinding {
        return ArchitectureFragmentBinding.inflate(inflater, container, false)
    }

    override fun observe() {
        viewModel.getArchitectureObserve().observe(this) {
            adapter.setList(it)
        }
    }

    override fun initView() {
        val flexboxLayoutManager = com.google.android.flexbox.FlexboxLayoutManager(requireContext())
        flexboxLayoutManager.setFlexDirection(com.google.android.flexbox.FlexDirection.ROW)
        flexboxLayoutManager.setFlexWrap(com.google.android.flexbox.FlexWrap.WRAP)
        flexboxLayoutManager.setJustifyContent(com.google.android.flexbox.JustifyContent.FLEX_START)
        flexboxLayoutManager.setAlignItems(com.google.android.flexbox.AlignItems.STRETCH)

        adapter = ArchitectureAdapter()
        binding?.recyclerview?.layoutManager = flexboxLayoutManager
        binding?.recyclerview?.adapter = adapter
    }

    override fun initData() {
        viewModel.getArchitectureData()
    }
}