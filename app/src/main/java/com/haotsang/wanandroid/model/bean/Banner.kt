package com.haotsang.wanandroid.model.bean

import androidx.annotation.Keep

@Keep
data class Banner(
    val desc: String,
    val id: Long,
    val imagePath: String,
    val isVisible: Int,
    val order: Int,
    val title: String,
    val type: Int,
    val url: String
)