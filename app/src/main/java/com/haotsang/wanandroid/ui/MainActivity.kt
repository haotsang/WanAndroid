package com.haotsang.wanandroid.ui

import android.view.LayoutInflater
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.haotsang.wanandroid.R
import com.haotsang.wanandroid.base.BaseViewBindingActivity
import com.haotsang.wanandroid.databinding.MainActivittyBinding
import com.haotsang.wanandroid.ui.architecture.ArchitectureRootFragment
import com.haotsang.wanandroid.ui.home.HomeFragment
import com.haotsang.wanandroid.ui.profile.ProfileFragment
import com.haotsang.wanandroid.ui.question.QuestionFragment
import com.haotsang.wanandroid.utils.ext.reduceDragSensitivity

class MainActivity : BaseViewBindingActivity<MainActivittyBinding>() {

    override fun getViewBinding(inflater: LayoutInflater): MainActivittyBinding {
        return MainActivittyBinding.inflate(layoutInflater)
    }

    override fun initView() {
        val adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount() = 4
            override fun createFragment(position: Int) = when(position) {
                0 -> HomeFragment()
                1 -> QuestionFragment()
                2 -> ArchitectureRootFragment()
                3 -> ProfileFragment()
                else -> throw IllegalStateException("Invalid position")
            }
        }
        binding.viewpager.reduceDragSensitivity()
        binding.viewpager.adapter = adapter
        binding.viewpager.offscreenPageLimit = ViewPager2.OFFSCREEN_PAGE_LIMIT_DEFAULT // 禁用预加载

        binding.viewpager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                when (position) {
                    0 -> binding.bottomNavigation.selectedItemId = R.id.nav_fragment1
                    1 -> binding.bottomNavigation.selectedItemId = R.id.nav_fragment2
                    2 -> binding.bottomNavigation.selectedItemId = R.id.nav_fragment3
                    3 -> binding.bottomNavigation.selectedItemId = R.id.nav_fragment4
                }
            }
        })
        binding.bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.nav_fragment1 -> binding.viewpager.setCurrentItem(0, true)
                R.id.nav_fragment2 -> binding.viewpager.setCurrentItem(1, true)
                R.id.nav_fragment3 -> binding.viewpager.setCurrentItem(2, true)
                R.id.nav_fragment4 -> binding.viewpager.setCurrentItem(3, true)
            }
            true
        }
    }
}