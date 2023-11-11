package com.haotsang.wanandroid.ui.points.rank

import com.haotsang.wanandroid.model.api.RetrofitClient

class PointsRankRepository {
    suspend fun getPointsRank(page: Int) =
        RetrofitClient.apiService.getPointsRank(page).apiData()
}