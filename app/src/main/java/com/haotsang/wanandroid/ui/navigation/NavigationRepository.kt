package com.haotsang.wanandroid.ui.navigation

import com.haotsang.wanandroid.model.api.RetrofitClient

class NavigationRepository {
    suspend fun getNavigation() = RetrofitClient.apiService.getNavigation().apiData()
}