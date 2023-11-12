package com.haotsang.wanandroid.ui.discovery.question

import com.haotsang.wanandroid.R
import com.haotsang.wanandroid.base.BaseFragment
import com.haotsang.wanandroid.base.BaseVmFragment
import com.haotsang.wanandroid.common.adapter.ArticleAdapter
import com.haotsang.wanandroid.common.loadmore.setLoadMoreStatus
import com.haotsang.wanandroid.databinding.QuestionFragmentBinding
import com.haotsang.wanandroid.model.bean.Article
import com.haotsang.wanandroid.ui.browser.BrowserFragment

class QuestionFragment : BaseVmFragment<QuestionFragmentBinding, QuestionViewModel>(QuestionFragmentBinding::inflate) {

    private lateinit var articleAdapter: ArticleAdapter

    override fun viewModelClass(): Class<QuestionViewModel> = QuestionViewModel::class.java

    override fun observe() {
        super.observe()

        mViewModel.apply {
            getQuestionObserve().observe(viewLifecycleOwner) {
                articleAdapter.setList(it)
            }
            loadMoreStatus.observe(viewLifecycleOwner) {
                articleAdapter.loadMoreModule.setLoadMoreStatus(it)
            }

            reloadStatus.observe(viewLifecycleOwner) {
//                mBinding?.reloadView?.root?.isVisible = it
            }
        }
    }
    override fun initView() {
        articleAdapter = ArticleAdapter().also {
            it.loadMoreModule.setOnLoadMoreListener {
                mViewModel.loadMoreQuestionList()
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
            mBinding?.rvList?.adapter = it
        }
    }


    override fun initData() {
        super.initData()
        mViewModel.refreshQuestionList()
    }
}