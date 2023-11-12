package com.haotsang.wanandroid.ui.main

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.haotsang.wanandroid.R
import com.haotsang.wanandroid.base.BaseFragment
import com.haotsang.wanandroid.databinding.MainFragmentBinding
import com.haotsang.wanandroid.ui.architecture.ArchitectureFragment
import com.haotsang.wanandroid.ui.discovery.DiscoveryFragment
import com.haotsang.wanandroid.ui.home.HomeFragment
import com.haotsang.wanandroid.ui.profile.ProfileFragment

class MainFragment : BaseFragment<MainFragmentBinding>(MainFragmentBinding::inflate) {

    private lateinit var fragments: Map<Int, Fragment>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, object :
            OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (activity!!.supportFragmentManager.backStackEntryCount > 0) {
                    activity!!.supportFragmentManager.popBackStack()
                    return
                }

                if (mBinding?.bottomNavigation?.selectedItemId != R.id.nav_fragment1) {
                    mBinding?.bottomNavigation?.selectedItemId = R.id.nav_fragment1
                } else {
                    requireActivity().finish()
                }
            }
        })

        fragments = mapOf(
            R.id.nav_fragment1 to createFragment(HomeFragment::class.java),
            R.id.nav_fragment2 to createFragment(DiscoveryFragment::class.java),
            R.id.nav_fragment3 to createFragment(ArchitectureFragment::class.java),
            R.id.nav_fragment4 to createFragment(ProfileFragment::class.java),
        )

        mBinding?.bottomNavigation?.setOnItemSelectedListener {
            showFragment(it.itemId)
            true
        }

        if (savedInstanceState == null) {
            val initialItemId = R.id.nav_fragment1
            mBinding?.bottomNavigation?.selectedItemId = initialItemId
//            showFragment(initialItemId)
        }

    }

    private fun createFragment(clazz: Class<out Fragment>): Fragment {
        var fragment = requireActivity().supportFragmentManager.fragments.find { it.javaClass == clazz }
        if (fragment == null) {
            fragment = when (clazz) {
                HomeFragment::class.java -> HomeFragment()
                DiscoveryFragment::class.java -> DiscoveryFragment()
                ArchitectureFragment::class.java -> ArchitectureFragment()
                ProfileFragment::class.java -> ProfileFragment()
                else -> throw IllegalArgumentException("argument ${clazz.simpleName} is illegal")
            }
        }
        return fragment
    }

    private fun showFragment(menuItemId: Int) {
        val currentFragment = requireActivity().supportFragmentManager.fragments.find {
            it.isVisible && it in fragments.values
        }
        val targetFragment = fragments[menuItemId]
        requireActivity().supportFragmentManager.beginTransaction().apply {
            currentFragment?.let { if (it.isVisible) hide(it) }
            targetFragment?.let {
                if (it.isAdded) show(it) else add(R.id.main_frame, it)
            }
        }.commit()
    }
}