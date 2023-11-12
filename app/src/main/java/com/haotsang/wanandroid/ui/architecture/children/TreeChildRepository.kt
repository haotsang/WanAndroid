package com.haotsang.wanandroid.ui.architecture.children

import com.haotsang.wanandroid.model.api.RetrofitClient

class TreeChildRepository {

    suspend fun getArticleListByCid(page: Int, cid: Int) = RetrofitClient.apiService.getArticleChildren(page, cid).apiData()

}