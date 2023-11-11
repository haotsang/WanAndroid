package com.haotsang.wanandroid.ui.register

import com.haotsang.wanandroid.model.api.RetrofitClient

class RegisterRepository {
    suspend fun register(username: String, password: String, repassword: String) =
        RetrofitClient.apiService.register(username, password, repassword).apiData()
}