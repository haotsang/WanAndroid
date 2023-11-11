package com.haotsang.wanandroid.ui.login

import com.haotsang.wanandroid.model.api.RetrofitClient

class LoginRepository {
    suspend fun login(username: String, password: String) =
        RetrofitClient.apiService.login(username, password).apiData()
}