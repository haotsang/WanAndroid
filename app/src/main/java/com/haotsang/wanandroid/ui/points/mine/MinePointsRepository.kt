package com.haotsang.wanandroid.ui.points.mine

import com.haotsang.wanandroid.model.api.RetrofitClient

class MinePointsRepository {
    suspend fun getMyPoints() = RetrofitClient.apiService.getPoints().apiData()
    suspend fun getPointsRecord(page: Int) =
        RetrofitClient.apiService.getPointsRecord(page).apiData()
}