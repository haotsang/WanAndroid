package com.haotsang.wanandroid.model.api

import com.haotsang.wanandroid.model.bean.Banner
import com.haotsang.wanandroid.model.bean.Category
import com.haotsang.wanandroid.model.bean.Navigation
import retrofit2.http.GET

interface ApiService {

    companion object {
        const val BASE_URL = "https://www.wanandroid.com"
    }

    /**
     * 首页banner
     * @see <a href="https://www.wanandroid.com/banner/json">查看示例</a>
     */
    @GET("banner/json")
    suspend fun getBanners(): ApiResult<List<Banner>>

    /**
     * 导航数据
     * @see <a href="https://www.wanandroid.com/navi/json">查看示例</a>
     */
    @GET("navi/json")
    suspend fun getNavigation(): ApiResult<List<Navigation>>

    /**
     * 体系数据
     * @see <a href="https://www.wanandroid.com/tree/json">查看示例</a>
     */
    @GET("tree/json")
    suspend fun getArticleCategories(): ApiResult<MutableList<Category>>
}