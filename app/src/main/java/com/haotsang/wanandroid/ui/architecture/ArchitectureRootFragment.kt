package com.haotsang.wanandroid.ui.architecture

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.haotsang.wanandroid.base.BaseViewBindingFragment
import com.haotsang.wanandroid.databinding.ArchitectureRootFragmentBinding
import com.haotsang.wanandroid.ui.navigation.NavigationFragment
import com.haotsang.wanandroid.utils.ext.reduceDragSensitivity

class ArchitectureRootFragment : BaseViewBindingFragment<ArchitectureRootFragmentBinding>() {

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): ArchitectureRootFragmentBinding {
        return ArchitectureRootFragmentBinding.inflate(inflater, container, false)
    }

    override fun observe() {

    }

    override fun initView() {
        val tabs = arrayOf("体系", "导航")

        val adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount() = tabs.size
            override fun createFragment(position: Int) = when(position) {
                0 -> ArchitectureFragment()
                1 -> NavigationFragment()
                else -> throw IllegalStateException("Invalid position")
            }
        }

        binding?.viewpager?.reduceDragSensitivity()
        binding?.viewpager?.adapter = adapter
        binding?.viewpager?.offscreenPageLimit = ViewPager2.OFFSCREEN_PAGE_LIMIT_DEFAULT // 禁用预加载

        TabLayoutMediator(binding?.tabLayout!!, binding?.viewpager!!) { tab, position ->
            tab.text = tabs[position]
        }.attach()

    }

    override fun initData() {

    }
}