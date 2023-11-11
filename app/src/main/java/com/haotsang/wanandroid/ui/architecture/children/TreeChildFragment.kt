package com.haotsang.wanandroid.ui.architecture.children

import androidx.core.os.bundleOf
import com.haotsang.wanandroid.R
import com.haotsang.wanandroid.base.BaseVmFragment
import com.haotsang.wanandroid.databinding.TreeChildFragmentBinding
import com.haotsang.wanandroid.model.bean.Category
import com.haotsang.wanandroid.common.adapter.SimpleArticleAdapter

class TreeChildFragment :
    BaseVmFragment<TreeChildFragmentBinding, TreeChildViewModel>(TreeChildFragmentBinding::inflate) {

    private lateinit var mAdapter: SimpleArticleAdapter
    companion object {
        fun newInstance(position: Int, children: MutableList<Category>): TreeChildFragment {
            return TreeChildFragment().apply {
                arguments = bundleOf(Pair("children", children), Pair("position", position))
            }
        }
    }

    override fun viewModelClass(): Class<TreeChildViewModel>  = TreeChildViewModel::class.java

    override fun initView() {
        mAdapter = SimpleArticleAdapter().also {
            it.loadMoreModule.setOnLoadMoreListener {
//                mViewModel.loadMoreArticleList(categoryList[checkedPosition].id)
            }
            it.setOnItemClickListener { _, _, position ->
                val article = it.data[position]

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

    override fun observe() {
        super.observe()
        mViewModel.getArticleList().observe(this) { list ->
            mAdapter.setList(list)
        }
    }

    override fun initData() {
        super.initData()
        mViewModel.getTreeChild(0, 0)
    }


}