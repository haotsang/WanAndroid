package com.haotsang.wanandroid.model.bean

import java.io.Serializable


data class Tag(
    var articleId: Long = 0,
    var name: String = "",
    var url: String = ""
): Serializable