package com.haotsang.wanandroid.ui.points.mine

import androidx.lifecycle.MutableLiveData
import com.haotsang.wanandroid.base.BaseViewModel
import com.haotsang.wanandroid.common.loadmore.LoadMoreStatus
import com.haotsang.wanandroid.model.bean.PointRank
import com.haotsang.wanandroid.model.bean.PointRecord
import com.haotsang.wanandroid.utils.ext.concat

class MinePointsViewModel : BaseViewModel() {
    companion object {
        const val INITIAL_PAGE = 1
    }

    private val minePointsRespository by lazy { MinePointsRepository() }

    val totalPoints = MutableLiveData<PointRank>()
    val pointsList = MutableLiveData<MutableList<PointRecord>>()

    val loadMoreStatus = MutableLiveData<LoadMoreStatus>()
    val refreshStatus = MutableLiveData<Boolean>()
    val reloadStatus = MutableLiveData<Boolean>()
    var page = INITIAL_PAGE

    fun refresh() {
        launch(
            block = {
                refreshStatus.value = true
                reloadStatus.value = false

                val points = minePointsRespository.getMyPoints()
                val pagination = minePointsRespository.getPointsRecord(INITIAL_PAGE)
                page = pagination.curPage
                totalPoints.value = points
                pointsList.value = pagination.datas

                refreshStatus.value = false
            },
            error = {
                refreshStatus.value = false
                reloadStatus.value = page == INITIAL_PAGE
            }
        )
    }

    fun loadMoreRecord() {
        launch(
            block = {
                loadMoreStatus.value = LoadMoreStatus.LOADING

                val pagination = minePointsRespository.getPointsRecord(page + 1)
                page = pagination.curPage
                pointsList.value = pointsList.value.concat(pagination.datas)

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