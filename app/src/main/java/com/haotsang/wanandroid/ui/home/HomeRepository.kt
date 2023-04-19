package com.haotsang.wanandroid.ui.home

import com.haotsang.wanandroid.model.api.RetrofitClient

class HomeRepository {

    suspend fun getBanners() = RetrofitClient.apiService.getBanners().apiData()

//    suspend fun getHotWords() = RetrofitClient.apiService.getHotWords().apiData()
}