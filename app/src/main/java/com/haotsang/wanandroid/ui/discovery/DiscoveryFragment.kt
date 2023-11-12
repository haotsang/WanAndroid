package com.haotsang.wanandroid.ui.discovery

import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.haotsang.wanandroid.base.BaseFragment
import com.haotsang.wanandroid.databinding.DiscoveryFragmentBinding
import com.haotsang.wanandroid.ui.discovery.navigation.NavigationFragment
import com.haotsang.wanandroid.ui.discovery.question.QuestionFragment
import com.haotsang.wanandroid.utils.ext.reduceDragSensitivity

class DiscoveryFragment : BaseFragment<DiscoveryFragmentBinding>(DiscoveryFragmentBinding::inflate) {

    override fun initialize() {
        super.initialize()
        val fragments = arrayOf(
                Pair(QuestionFragment(), "问答"),
                Pair(NavigationFragment(), "导航")
            )

        val adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount() = fragments.size
            override fun createFragment(position: Int) = fragments[position].first
        }

        mBinding?.viewpager?.reduceDragSensitivity()
        mBinding?.viewpager?.adapter = adapter
        mBinding?.viewpager?.offscreenPageLimit = ViewPager2.OFFSCREEN_PAGE_LIMIT_DEFAULT // 禁用预加载

        TabLayoutMediator(mBinding?.tabLayout!!, mBinding?.viewpager!!) { tab, position ->
            tab.text = fragments[position].second
        }.attach()

    }
}