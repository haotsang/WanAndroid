package com.haotsang.wanandroid.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.haotsang.wanandroid.base.BaseViewBindingFragment
import com.haotsang.wanandroid.common.adapter.BannerImageAdapter2
import com.haotsang.wanandroid.databinding.HomeFragmentBinding
import com.haotsang.wanandroid.model.bean.Banner
import com.haotsang.wanandroid.utils.BrowserUtils
import com.youth.banner.indicator.CircleIndicator

class HomeFragment : BaseViewBindingFragment<HomeFragmentBinding>() {

    private val viewModel : HomeViewModel by viewModels()
    // 等价于 ViewModelProvider(this)[HomeViewModel::class.java]

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): HomeFragmentBinding {
        return HomeFragmentBinding.inflate(inflater, container, false)
    }

    override fun observe() {
        viewModel.getBannerObserve().observe(this) {
            binding?.bannerView!!.setDatas(it)
        }
    }

    override fun initView() {
        initBanner()
    }

    override fun initData() {
        viewModel.getBannerData()
    }


    private fun initBanner() {
        binding?.bannerView!!.setAdapter(BannerImageAdapter2(mutableListOf(), requireContext()))
            .addBannerLifecycleObserver(this)
            .setIndicator(CircleIndicator(requireContext()))
            .setOnBannerListener { data, position ->
                BrowserUtils.openInBrowser(requireContext(), (data as Banner).url)
            }
    }


}