package com.haotsang.wanandroid.ui.points.rank

import androidx.lifecycle.MutableLiveData
import com.haotsang.wanandroid.base.BaseViewModel
import com.haotsang.wanandroid.common.loadmore.LoadMoreStatus
import com.haotsang.wanandroid.model.bean.PointRank
import com.haotsang.wanandroid.utils.ext.concat

class PointsRankViewModel : BaseViewModel() {
    companion object {
        const val INITIAL_PAGE = 1
    }

    private val pointsRankRespository by lazy { PointsRankRepository() }

    val pointsRank = MutableLiveData<MutableList<PointRank>>()

    val loadMoreStatus = MutableLiveData<LoadMoreStatus>()
    val refreshStatus = MutableLiveData<Boolean>()
    val reloadStatus = MutableLiveData<Boolean>()

    private var page = INITIAL_PAGE

    fun refreshData() {
        launch(
            block = {
                refreshStatus.value = true
                reloadStatus.value = false
                val pagination = pointsRankRespository.getPointsRank(INITIAL_PAGE)
                page = pagination.curPage
                pointsRank.value = pagination.datas
                refreshStatus.value = false
            },
            error = {
                reloadStatus.value = page == INITIAL_PAGE
            }
        )
    }

    fun loadMoreData() {
        launch(
            block = {
                loadMoreStatus.value = LoadMoreStatus.LOADING
                val pagination = pointsRankRespository.getPointsRank(page + 1)
                page = pagination.curPage
                pointsRank.value = pointsRank.value.concat(pagination.datas)

                loadMoreStatus.value = if (pagination.offset >= pagination.total) {
                    LoadMoreStatus.END
                } else {
                    LoadMoreStatus.COMPLETED
                }
            },
            error = { loadMoreStatus.value = LoadMoreStatus.ERROR }
        )
    }
}