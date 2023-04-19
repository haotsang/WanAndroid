package com.haotsang.wanandroid.model.bean

import androidx.annotation.Keep

@Keep
data class Navigation(
    val cid: Int,
    val name: String,
    val articles: MutableList<Article>
)