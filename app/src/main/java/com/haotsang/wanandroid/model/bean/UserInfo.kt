package com.haotsang.wanandroid.model.bean

data class UserInfo(
    val admin: Boolean,
    val email: String,
    val icon: String,
    val id: Int,
    val nickname: String,
    val password: String,
    val publicName: String,
    val token: String,
    val type: Int,
    val username: String,
    val collectIds: MutableList<Long>,
    val chapterTops: MutableList<Any>
)