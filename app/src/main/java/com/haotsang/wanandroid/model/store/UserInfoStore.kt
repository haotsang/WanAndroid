package com.haotsang.wanandroid.model.store

import com.haotsang.wanandroid.App
import com.haotsang.wanandroid.model.bean.UserInfo
import com.haotsang.wanandroid.utils.DataStoreUtils
import com.haotsang.wanandroid.utils.MoshiHelper

object UserInfoStore {

    /**
     * 获取本地sp存储的用户信息
     */
    fun getUserInfo(): UserInfo? {
        val userInfoStr = DataStoreUtils.getData<String>(DataStoreUtils.ds_user_info)
        return if (userInfoStr.isNotEmpty()) {
            MoshiHelper.fromJson<UserInfo>(userInfoStr)
        } else {
            null
        }
    }

    /**
     * 设置用户信息、保存本地sp
     */
    fun setUserInfo(userInfo: UserInfo) {
        DataStoreUtils.putData(DataStoreUtils.ds_user_info.first, MoshiHelper.toJson(userInfo))
    }


    /**
     * 清除用户信息
     */
    fun clearUserInfo() {
        DataStoreUtils.putData(DataStoreUtils.ds_user_info.first, "")
    }
}