package com.haotsang.wanandroid.model.store

import com.haotsang.wanandroid.model.api.RetrofitClient

/**
 * 是否登录，有cookie/token，且用户信息存在
 */
fun isLogin() = UserInfoStore.getUserInfo() != null && RetrofitClient.hasCookie()