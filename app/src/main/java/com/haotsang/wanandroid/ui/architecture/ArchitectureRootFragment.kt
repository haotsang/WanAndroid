package com.haotsang.wanandroid.ui.architecture

import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.haotsang.wanandroid.base.BaseFragment
import com.haotsang.wanandroid.databinding.ArchitectureRootFragmentBinding
import com.haotsang.wanandroid.ui.navigation.NavigationFragment
import com.haotsang.wanandroid.utils.ext.reduceDragSensitivity

class ArchitectureRootFragment : BaseFragment<ArchitectureRootFragmentBinding>(ArchitectureRootFragmentBinding::inflate) {

    override fun initialize() {
        super.initialize()
        val tabs = arrayOf("体系", "导航")

        val adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount() = tabs.size
            override fun createFragment(position: Int) = when(position) {
                0 -> ArchitectureFragment()
                1 -> NavigationFragment()
                else -> throw IllegalStateException("Invalid position")
            }
        }

        mBinding?.viewpager?.reduceDragSensitivity()
        mBinding?.viewpager?.adapter = adapter
        mBinding?.viewpager?.offscreenPageLimit = ViewPager2.OFFSCREEN_PAGE_LIMIT_DEFAULT // 禁用预加载

        TabLayoutMediator(mBinding?.tabLayout!!, mBinding?.viewpager!!) { tab, position ->
            tab.text = tabs[position]
        }.attach()

    }
}