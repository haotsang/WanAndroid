package com.haotsang.wanandroid.ui.search

import com.haotsang.wanandroid.model.api.RetrofitClient

class SearchRepository {

    suspend fun getHotWords() = RetrofitClient.apiService.getHotWords().apiData()

    fun getSearchHistory() = listOf("apple", "retrofit", "okhttp", "google", "glide", "jetpack")

}