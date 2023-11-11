package com.haotsang.wanandroid.ui.search

import android.view.LayoutInflater
import android.widget.TextView
import com.haotsang.wanandroid.R
import com.haotsang.wanandroid.base.BaseVmFragment
import com.haotsang.wanandroid.databinding.SearchFragmentBinding
import com.haotsang.wanandroid.ui.browser.BrowserFragment

class SearchFragment :
    BaseVmFragment<SearchFragmentBinding, SearchViewModel>(SearchFragmentBinding::inflate) {

    override fun viewModelClass(): Class<SearchViewModel> = SearchViewModel::class.java

    override fun initView() {

    }

    override fun observe() {
        super.observe()
        mViewModel.getHotWordsObserve().observe(this) { list ->
            mBinding?.flHotSearch?.removeAllViews()
            for (child in list.orEmpty()) {
                val tvChild = LayoutInflater.from(mBinding?.flHotSearch?.context).inflate(
                    R.layout.architecture_rv_child_item,
                    mBinding?.flHotSearch,
                    false
                ) as? TextView

                tvChild?.let {
                    it.text = child.name
//                    it.setOnClickListener { BrowserFragment.openUrl(requireContext(), child.link) }
                    mBinding?.flHotSearch?.addView(it)
                }
            }
        }

        mViewModel.getHistoryObserve().observe(this) { list ->
            mBinding?.flSearchHistory?.removeAllViews()
            for (text in list.orEmpty()) {
                val tvChild = LayoutInflater.from(mBinding?.flSearchHistory?.context).inflate(
                    R.layout.architecture_rv_child_item,
                    mBinding?.flSearchHistory,
                    false
                ) as? TextView

                tvChild?.let {
                    it.text = text
//                    it.setOnClickListener { BrowserFragment.openUrl(requireContext(), text) }
                    mBinding?.flSearchHistory?.addView(it)
                }
            }
        }
    }

    override fun initData() {
        super.initData()
        mViewModel.getHotWords()
        mViewModel.getSearchHistory()
    }


}