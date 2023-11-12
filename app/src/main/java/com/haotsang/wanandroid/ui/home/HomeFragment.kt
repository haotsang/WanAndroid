package com.haotsang.wanandroid.ui.home

import androidx.core.view.isVisible
import com.haotsang.wanandroid.R
import com.haotsang.wanandroid.base.BaseVmFragment
import com.haotsang.wanandroid.common.adapter.ArticleAdapter
import com.haotsang.wanandroid.common.adapter.BannerImageAdapter2
import com.haotsang.wanandroid.common.loadmore.setLoadMoreStatus
import com.haotsang.wanandroid.databinding.HomeFragmentBinding
import com.haotsang.wanandroid.model.bean.Article
import com.haotsang.wanandroid.model.bean.Banner
import com.haotsang.wanandroid.ui.browser.BrowserFragment
import com.youth.banner.indicator.CircleIndicator

class HomeFragment :
    BaseVmFragment<HomeFragmentBinding, HomeViewModel>(HomeFragmentBinding::inflate) {

    private lateinit var articleAdapter: ArticleAdapter

    override fun viewModelClass(): Class<HomeViewModel> = HomeViewModel::class.java

    override fun observe() {
        mViewModel.apply {
            getBannerObserve().observe(viewLifecycleOwner) {
                mBinding?.bannerView!!.setDatas(it)
            }
            getArticleObserve().observe(viewLifecycleOwner) {
                articleAdapter.setList(it)
            }

            loadMoreStatus.observe(viewLifecycleOwner) {
                articleAdapter.loadMoreModule.setLoadMoreStatus(it)
            }

            refreshStatus.observe(viewLifecycleOwner) {
                mBinding?.progressBar?.isVisible = it
            }

            reloadStatus.observe(viewLifecycleOwner) {
                mBinding?.reloadView?.root?.isVisible = it
            }
        }
    }

    override fun initView() {
        mBinding?.bannerView!!.setAdapter(BannerImageAdapter2(mutableListOf(), requireContext()))
            .addBannerLifecycleObserver(this)
            .setIndicator(CircleIndicator(requireContext()))
            .setOnBannerListener { data, position ->
                val banner: Banner = (data as Banner)
                BrowserFragment.openUrl(this, Triple(banner.id, banner.title, banner.url))
            }
            .start()


        articleAdapter = ArticleAdapter().also {
            it.loadMoreModule.setOnLoadMoreListener {
                mViewModel.loadMoreArticleList()
            }
            it.setOnItemClickListener { adapter, v, position ->
                val article: Article = it.data[position]
                BrowserFragment.openUrl(this, Triple(article.id, article.title, article.link))
            }

            it.setOnItemChildClickListener { _, view, position ->
                val article = it.data[position]
                if (view.id == R.id.iv_collect) {
                    view.isSelected = !view.isSelected
//                    if (article.collect) {
//                        mViewModel.uncollect(article.id)
//                    } else {
//                        mViewModel.collect(article.id)
//                    }
                }
            }
            mBinding?.recyclerView?.adapter = it
        }

        mBinding?.reloadView?.btnReload?.setOnClickListener {
            initData()
        }
    }

    override fun initData() {
        mViewModel.getBannerData()
        mViewModel.getArticleData()
    }

}