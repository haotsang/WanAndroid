package com.haotsang.wanandroid.ui.architecture

import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.haotsang.wanandroid.base.BaseVmFragment
import com.haotsang.wanandroid.databinding.ArchitectureFragmentBinding
import com.haotsang.wanandroid.ui.architecture.children.TreeChildFragment
import com.haotsang.wanandroid.ui.main.MainActivity
import com.haotsang.wanandroid.utils.ext.setOnItemClickListener

class ArchitectureFragment :
    BaseVmFragment<ArchitectureFragmentBinding, ArchitectureViewModel>(ArchitectureFragmentBinding::inflate) {

    private lateinit var adapter: ArchitectureAdapter

    override fun viewModelClass(): Class<ArchitectureViewModel> = ArchitectureViewModel::class.java

    override fun observe() {
        mViewModel.getArchitectureObserve().observe(this) {
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
        mBinding?.recyclerview?.layoutManager = flexboxLayoutManager
        mBinding?.recyclerview?.adapter = adapter


    }

    override fun initData() {
        mViewModel.getArchitectureData()
    }

    override fun setListener() {

    }
}