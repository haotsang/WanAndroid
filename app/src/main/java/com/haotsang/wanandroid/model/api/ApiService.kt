package com.haotsang.wanandroid.model.api

import com.haotsang.wanandroid.model.bean.Article
import com.haotsang.wanandroid.model.bean.Banner
import com.haotsang.wanandroid.model.bean.Category
import com.haotsang.wanandroid.model.bean.HotWord
import com.haotsang.wanandroid.model.bean.Navigation
import com.haotsang.wanandroid.model.bean.Pagination
import com.haotsang.wanandroid.model.bean.PointRank
import com.haotsang.wanandroid.model.bean.PointRecord
import com.haotsang.wanandroid.model.bean.UserInfo
import com.haotsang.wanandroid.model.core.StatusData
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    companion object {
        const val BASE_URL = "https://www.wanandroid.com/"
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
    suspend fun getNavigation(): ApiResult<MutableList<Navigation>>

    /**
     * 体系数据
     * @see <a href="https://www.wanandroid.com/tree/json">查看示例</a>
     */
    @GET("tree/json")
    suspend fun getArticleCategories(): ApiResult<MutableList<Category>>

    /**
     * 知识体系下的文章
     * 方法：GET
     * 参数：
     * cid 分类的id，上述二级目录的id
     * 页码：拼接在链接上，从0开始。
     */
    @GET("article/list/{page}/json?")
    suspend fun getArticleChildren(
        @Path("page") page: Int,
        @Query("cid") cid: Int
    ): ApiResult<Pagination<Article>>


    @GET("hotkey/json")
    suspend fun getHotWords(): ApiResult<List<HotWord>>


    //-----------------------【登录注册】----------------------
    @FormUrlEncoded
    @POST("user/login")
    suspend fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ): ApiResult<UserInfo>

    @GET("user/logout/json")
    suspend fun logOut(): ApiResult<Any>

    @FormUrlEncoded
    @POST("user/register")
    suspend fun register(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("repassword") repassword: String
    ): ApiResult<UserInfo>


    @GET("lg/coin/userinfo/json")
    suspend fun getPoints(): ApiResult<PointRank>

    @GET("lg/coin/list/{page}/json")
    suspend fun getPointsRecord(@Path("page") page: Int): ApiResult<Pagination<PointRecord>>

    @GET("coin/rank/{page}/json")
    suspend fun getPointsRank(@Path("page") page: Int): ApiResult<Pagination<PointRank>>


    /**
     * 热门
     */
    @GET("/article/top/json")
    suspend fun getTopArticleList(): ApiResult<List<Article>>
    /**
     * 热门文章
     */
    @GET("/article/list/{page}/json")
    suspend fun getArticleList(@Path("page") page: Int): ApiResult<Pagination<Article>>


    /**
     * 问答
     * pageId,拼接在链接上，例如上面的1
     */
    @GET("wenda/list/{page}/json")
    suspend fun getQuestionList(@Path("page") page: Int): ApiResult<Pagination<Article>>

}