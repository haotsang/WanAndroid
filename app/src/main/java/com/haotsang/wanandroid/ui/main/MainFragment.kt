package com.haotsang.wanandroid.ui.main

import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.haotsang.wanandroid.R
import com.haotsang.wanandroid.base.BaseVmFragment
import com.haotsang.wanandroid.databinding.MainFragmentBinding
import com.haotsang.wanandroid.ui.architecture.ArchitectureRootFragment
import com.haotsang.wanandroid.ui.home.HomeFragment
import com.haotsang.wanandroid.ui.profile.ProfileFragment
import com.haotsang.wanandroid.ui.question.QuestionFragment
import com.haotsang.wanandroid.ui.search.SearchFragment
import com.haotsang.wanandroid.utils.ext.reduceDragSensitivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainFragment :
    BaseVmFragment<MainFragmentBinding, MainViewModel>(MainFragmentBinding::inflate) {

    override fun viewModelClass(): Class<MainViewModel> = MainViewModel::class.java

    override fun initView() {
        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, object :
            OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (activity!!.supportFragmentManager.backStackEntryCount > 0) {
                    activity!!.supportFragmentManager.popBackStack()
                    return
                }

                if (mBinding?.viewpager?.currentItem != 0) {
                    mBinding?.viewpager?.setCurrentItem(0, true)
                } else {
                    requireActivity().finish()
                }
            }
        })

        mBinding?.toolbar?.apply {

            menu.findItem(R.id.action_search).setOnMenuItemClickListener {
                (activity as MainActivity).switchFragmentPage(SearchFragment())
                true
            }

        }


        val fragments = listOf(HomeFragment(), QuestionFragment(), ArchitectureRootFragment(), ProfileFragment())
        val adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount() = fragments.size
            override fun createFragment(position: Int) = fragments[position]
        }

        mBinding?.viewpager?.reduceDragSensitivity()
        mBinding?.viewpager?.adapter = adapter
        mBinding?.viewpager?.offscreenPageLimit = ViewPager2.OFFSCREEN_PAGE_LIMIT_DEFAULT // 禁用预加载
//        mBinding?.viewpager?.isSaveEnabled = false // Fragment no longer exists for key f0: index 5
        mBinding?.viewpager?.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                when (position) {
                    0 -> mBinding?.bottomNavigation?.selectedItemId = R.id.nav_fragment1
                    1 -> mBinding?.bottomNavigation?.selectedItemId = R.id.nav_fragment2
                    2 -> mBinding?.bottomNavigation?.selectedItemId = R.id.nav_fragment3
                    3 -> mBinding?.bottomNavigation?.selectedItemId = R.id.nav_fragment4
                    else -> throw IllegalStateException("Invalid position")
                }

                val selectedId = mBinding?.bottomNavigation?.selectedItemId ?: return
                mBinding?.toolbar?.title =
                    mBinding?.bottomNavigation?.menu?.findItem(selectedId)?.title ?: ""
            }
        })
        mBinding?.bottomNavigation?.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.nav_fragment1 -> {
                    mBinding?.viewpager?.setCurrentItem(0, true)
                    return@setOnItemSelectedListener true
                }
                R.id.nav_fragment2 -> {
                    mBinding?.viewpager?.setCurrentItem(1, true)
                    return@setOnItemSelectedListener true
                }
                R.id.nav_fragment3 -> {
                    mBinding?.viewpager?.setCurrentItem(2, true)
                    return@setOnItemSelectedListener true
                }
                R.id.nav_fragment4 -> {
                    mBinding?.viewpager?.setCurrentItem(3, true)
                    return@setOnItemSelectedListener true
                }
            }
            false
        }


        mBinding?.pullRefreshLayout?.setOnRefreshListener {
//            val cur = fragments[mBinding?.viewpager?.currentItem ?: 0]
//            if (cur is HomeFragment) {
//                cur.childFragmentManager.f
//                cur.initData()
//                mBinding?.pullRefreshLayout?.isRefreshing = false
//            } else {
                lifecycleScope.launch {
                    delay(2000)
                    mBinding?.pullRefreshLayout?.isRefreshing = false
                }
//            }
        }
        mBinding?.pullRefreshLayout?.setOnChildScrollUpCallback { parent, child ->
            mBinding?.viewpager?.currentItem == 0 || mBinding?.viewpager?.currentItem == 3
//            false
        }

    }


}