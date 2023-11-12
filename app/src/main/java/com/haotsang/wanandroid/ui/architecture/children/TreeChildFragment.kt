package com.haotsang.wanandroid.ui.architecture.children

import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.haotsang.wanandroid.R
import com.haotsang.wanandroid.base.BaseVmFragment
import com.haotsang.wanandroid.databinding.TreeChildFragmentBinding
import com.haotsang.wanandroid.model.bean.Category
import com.haotsang.wanandroid.common.adapter.SimpleArticleAdapter
import com.haotsang.wanandroid.common.loadmore.setLoadMoreStatus
import com.haotsang.wanandroid.ui.browser.BrowserFragment

class TreeChildFragment :
    BaseVmFragment<TreeChildFragmentBinding, TreeChildViewModel>(TreeChildFragmentBinding::inflate) {

    private lateinit var categoryList: List<Category>
    var checkedPosition = 0
    private lateinit var mAdapter: SimpleArticleAdapter
    private lateinit var categoryAdapter: CategoryAdapter

    companion object {
        private const val CATEGORY_LIST = "CATEGORY_LIST"

        fun newInstance(children: MutableList<Category>): TreeChildFragment {
            return TreeChildFragment().apply {
                arguments = bundleOf(Pair(CATEGORY_LIST, children))
            }
        }
    }

    override fun viewModelClass(): Class<TreeChildViewModel>  = TreeChildViewModel::class.java

    override fun initView() {
        categoryList = arguments?.getParcelableArrayList(CATEGORY_LIST)!!
        checkedPosition = 0

        initCategoryAdapter()
        initArticleAdapter()

    }

    override fun observe() {
        super.observe()
        mViewModel.run {
            getArticleList().observe(viewLifecycleOwner) { list ->
                mAdapter.setList(list)
            }

            refreshStatus.observe(viewLifecycleOwner) {
                mBinding?.swipeRefreshLayout?.isRefreshing = it
            }

            loadMoreStatus.observe(viewLifecycleOwner) {
                mAdapter.loadMoreModule.setLoadMoreStatus(it)
            }

            reloadStatus.observe(viewLifecycleOwner) {
                mBinding?.reloadView?.root?.isVisible = it
            }
        }
    }

    override fun lazyLoadData() {
        mViewModel.refreshArticleList(categoryList[checkedPosition].id)
    }

    override fun setListener() {
        super.setListener()
        mBinding?.swipeRefreshLayout?.run {
            setOnRefreshListener {
                mViewModel.refreshArticleList(categoryList[checkedPosition].id)
            }
        }
        mBinding?.reloadView?.btnReload?.setOnClickListener {
            mViewModel.refreshArticleList(categoryList[checkedPosition].id)
        }

    }

    private fun initCategoryAdapter() {
        categoryAdapter = CategoryAdapter().also {
            it.setList(categoryList)
            it.onCheckedListener = { position ->
                checkedPosition = position
                mViewModel.refreshArticleList(categoryList[checkedPosition].id)
            }
            mBinding?.rvCategory?.adapter = it
        }
    }

    private fun initArticleAdapter() {
        mAdapter = SimpleArticleAdapter().also {
            it.loadMoreModule.setOnLoadMoreListener {
                mViewModel.loadMoreArticleList(categoryList[checkedPosition].id)
            }
            it.setOnItemClickListener { _, _, position ->
                val article = it.data[position]
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
    }

    fun check(position: Int) {
        if (position != checkedPosition) {
            checkedPosition = position
            categoryAdapter.check(position)
            (mBinding?.rvCategory?.layoutManager as? LinearLayoutManager)
                ?.scrollToPositionWithOffset(position, 0)
            mViewModel.refreshArticleList(categoryList[checkedPosition].id)
        }
    }
}