package com.haotsang.wanandroid.model.api

import com.haotsang.wanandroid.App
import com.haotsang.wanandroid.utils.NetWorkUtils
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response

class WanCacheInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        if (!NetWorkUtils.isNetworkAvailable(App.instance)) {
            // 无网络时强制从缓存读取，如果缓存中没有数据会返回 504
            // FORCE_CACHE 里设置了 only-if-cached=true 和 maxStale
            // only-if-cached 顾名思义就是只有缓存中有数据才返回，没有时返回 504
            // maxStale 指可以取过期多久的数据，FORCE_CACHE 中设置为了Int最大值
            request = request.newBuilder()
                .cacheControl(CacheControl.FORCE_CACHE)
                .build()
        }
        val response = chain.proceed(request)
        if (!NetWorkUtils.isNetworkAvailable(App.instance)) {
            val maxAge = 60 * 60
            response.newBuilder()
                .removeHeader("Pragma")
                .header("Cache-Control", "public, max-age=$maxAge")
                .build()
        } else {
            val maxStale = 60 * 60 * 24 * 28 // tolerate 4-weeks stale
            response.newBuilder()
                .removeHeader("Pragma")
                .header("Cache-Control", "public, only-if-cached, max-stale=$maxStale")
                .build()
        }
        return response
    }
}