package com.haotsang.wanandroid.model.bean

import androidx.annotation.Keep
import java.io.Serializable

@Keep
data class Category(
    val courseId: Int,
    val id: Int,
    val name: String,
    val order: Int,
    val parentChapterId: Int,
    val userControlSetTop: Boolean,
    val visible: Int,
    val children: MutableList<Category>
) : Serializable