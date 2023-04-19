package com.haotsang.wanandroid.ui.architecture

import com.haotsang.wanandroid.model.api.RetrofitClient

class ArchitectureRepository {
    suspend fun getArchitecture() = RetrofitClient.apiService.getArticleCategories().apiData()
}