package com.haotsang.wanandroid.ui.discovery.question

import com.haotsang.wanandroid.model.api.RetrofitClient

class QuestionRepository {
    suspend fun getQuestionList(page: Int) = RetrofitClient.apiService.getQuestionList(page).apiData()
}