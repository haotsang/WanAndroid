package com.haotsang.wanandroid.model.bean

import androidx.annotation.Keep

@Keep
data class Pagination<T>(
    val offset: Int,
    val size: Int,
    val total: Int,
    val pageCount: Int,
    val curPage: Int,
    val over: Boolean,
    val datas: MutableList<T>
)