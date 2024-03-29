package com.haotsang.wanandroid.ui.discovery.navigation

import com.haotsang.wanandroid.base.BaseVmFragment
import com.haotsang.wanandroid.databinding.NavigationFragmentBinding
import com.haotsang.wanandroid.ui.browser.BrowserFragment

class NavigationFragment :
    BaseVmFragment<NavigationFragmentBinding, NavigationViewModel>(NavigationFragmentBinding::inflate) {

    private lateinit var adapter: NavigationAdapter

    override fun viewModelClass(): Class<NavigationViewModel>  = NavigationViewModel::class.java

    override fun observe() {
        mViewModel.getNavigationObserve().observe(this) {
            adapter.setList(it)
        }
    }

    override fun initView() {
        val flexboxLayoutManager = com.google.android.flexbox.FlexboxLayoutManager(requireContext())
        flexboxLayoutManager.setFlexDirection(com.google.android.flexbox.FlexDirection.ROW)
        flexboxLayoutManager.setFlexWrap(com.google.android.flexbox.FlexWrap.WRAP)
        flexboxLayoutManager.setJustifyContent(com.google.android.flexbox.JustifyContent.FLEX_START)
        flexboxLayoutManager.setAlignItems(com.google.android.flexbox.AlignItems.STRETCH)

        adapter = NavigationAdapter()
        adapter.onItemClickListener = { article ->
            BrowserFragment.openUrl(this.parentFragment, Triple(article.id, article.title, article.link))
        }
        mBinding?.recyclerview?.layoutManager = flexboxLayoutManager
        mBinding?.recyclerview?.adapter = adapter
    }

    override fun initData() {
        mViewModel.getNavigationData()
    }
}