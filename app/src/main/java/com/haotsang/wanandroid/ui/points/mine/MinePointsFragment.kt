package com.haotsang.wanandroid.ui.points.mine

import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.core.view.isVisible
import com.haotsang.wanandroid.R
import com.haotsang.wanandroid.base.BaseVmFragment
import com.haotsang.wanandroid.common.loadmore.setLoadMoreStatus
import com.haotsang.wanandroid.databinding.MinePointsFragmentBinding
import com.haotsang.wanandroid.ui.main.MainActivity
import com.haotsang.wanandroid.ui.points.rank.PointsRankFragment

class MinePointsFragment :
    BaseVmFragment<MinePointsFragmentBinding, MinePointsViewModel>(MinePointsFragmentBinding::inflate) {

    private lateinit var mAdapter: MinePointsAdapter
    private lateinit var mHeaderView: View

    override fun viewModelClass(): Class<MinePointsViewModel> = MinePointsViewModel::class.java

    override fun initView() {
        mHeaderView = LayoutInflater.from(requireContext()).inflate(R.layout.mine_points_header, null)

        mAdapter = MinePointsAdapter().also {
            it.loadMoreModule.setOnLoadMoreListener { mViewModel.loadMoreRecord() }
            mBinding?.recyclerView?.adapter = it
        }

        mBinding?.toolbar?.setNavigationOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
        mBinding?.toolbar?.menu?.apply {
            add(0,0,0,"rank").setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM)
            findItem(0).setOnMenuItemClickListener {
                (requireActivity() as MainActivity).switchFragmentPage(PointsRankFragment())
                true
            }
        }

        mBinding?.swipeRefreshLayout?.run {
            setOnRefreshListener { mViewModel.refresh() }
        }

        mBinding?.reloadView?.btnReload?.setOnClickListener { mViewModel.refresh() }



    }

    override fun observe() {
        super.observe()
        mViewModel.run {
            totalPoints.observe(viewLifecycleOwner) {
                if (mAdapter.headerLayoutCount == 0) {
                    mAdapter.setHeaderView(mHeaderView)
                }

                mHeaderView.findViewById<TextView>(R.id.tvTotalPoints).text = it.coinCount.toString()
                mHeaderView.findViewById<TextView>(R.id.tvLevelRank).text = getString(R.string.level_rank, it.level, it.rank)
            }
            pointsList.observe(viewLifecycleOwner) {
                mAdapter.setList(it)
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
        super.initData()
        mViewModel.refresh()
    }
}