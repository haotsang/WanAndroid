package com.haotsang.wanandroid.ui.points.rank

import androidx.core.view.isVisible
import com.haotsang.wanandroid.base.BaseVmFragment
import com.haotsang.wanandroid.common.loadmore.setLoadMoreStatus
import com.haotsang.wanandroid.databinding.PointsRankFragmentBinding

class PointsRankFragment :
    BaseVmFragment<PointsRankFragmentBinding, PointsRankViewModel>(PointsRankFragmentBinding::inflate) {

    private lateinit var mAdapter: PointsRankAdapter

    override fun viewModelClass(): Class<PointsRankViewModel> = PointsRankViewModel::class.java

    override fun initView() {
        mBinding?.toolbar?.setNavigationOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        mAdapter = PointsRankAdapter().also {
            it.loadMoreModule.setOnLoadMoreListener { mViewModel.loadMoreData() }
            mBinding?.recyclerView?.adapter = it
        }

        mBinding?.swipeRefreshLayout?.run {
            setOnRefreshListener { mViewModel.refreshData() }
        }

        mBinding?.reloadView?.btnReload?.setOnClickListener { mViewModel.refreshData() }

    }

    override fun observe() {
        super.observe()
        mViewModel.run {
            pointsRank.observe(viewLifecycleOwner) {
                mAdapter.setNewInstance(it)
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

    override fun initData() {
        mViewModel.refreshData()
    }


}