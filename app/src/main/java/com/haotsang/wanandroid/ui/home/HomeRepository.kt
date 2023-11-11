package com.haotsang.wanandroid.ui.home

import com.haotsang.wanandroid.model.api.RetrofitClient

class HomeRepository {

    suspend fun getBanners() = RetrofitClient.apiService.getBanners().apiData()

    suspend fun getTopArticleList() = RetrofitClient.apiService.getTopArticleList().apiData()
    suspend fun getArticleList(page: Int) = RetrofitClient.apiService.getArticleList(page).apiData()

}