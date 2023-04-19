package com.haotsang.wanandroid.model.api

import android.util.Log
import com.haotsang.wanandroid.App
import com.haotsang.wanandroid.utils.MoshiHelper
import com.haotsang.wanandroid.utils.NetWorkUtils
import com.youth.banner.BuildConfig
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.internal.cache.CacheInterceptor
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

object RetrofitClient {

    /**log**/
    private val logger = HttpLoggingInterceptor.Logger {
        if (BuildConfig.DEBUG) {
            Log.i(this::class.simpleName, it)
        }
    }
    private val logInterceptor = HttpLoggingInterceptor(logger).apply {
        level = HttpLoggingInterceptor.Level.BODY
    }


    //设置缓存 10M
    private val cache = Cache(File(App.instance.cacheDir, "okhttpCache"), 10 * 1024 * 1024)
    /**
     * 有网时候的缓存
     */
    private val netCacheInterceptor  = Interceptor { chain ->
        val request = chain.request()
        val response = chain.proceed(request)
        val onlineCacheTime = 30 //在线的时候的缓存过期时间，如果想要不缓存，直接时间设置为0
        response.newBuilder()
            .header("Cache-Control", "public, max-age=$onlineCacheTime")
            .removeHeader("Pragma")
            .build()
    }
    /**
     * 没有网时候的缓存
     */
    private val offlineCacheInterceptor  = Interceptor { chain ->
        var request = chain.request()
        if (!NetWorkUtils.isNetworkAvailable(App.instance)) {
            if ("GET" == request.method) {
                val maxStale = 60 * 60 * 24 * 28 // tolerate 4-weeks stale
                request = request.newBuilder()
                    .header("Cache-Control", "public, only-if-cached, max-stale=$maxStale")
                    .build()
            } else if ("POST" == request.method) {

            }
        }
        chain.proceed(request)
    }



    /**OkhttpClient*/
    private val okHttpClient = OkHttpClient.Builder()
        .callTimeout(10, TimeUnit.SECONDS)
        .retryOnConnectionFailure(true)
        .cache(cache)
        .addInterceptor(offlineCacheInterceptor)
        .addNetworkInterceptor(netCacheInterceptor)
        .addNetworkInterceptor(logInterceptor)
        .build()

    /**Retrofit*/
    private val retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(ApiService.BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(MoshiHelper.moshi))
        .build()

    /**ApiService*/
    val apiService: ApiService = retrofit.create(ApiService::class.java)

}