package com.haotsang.wanandroid.ui.register

import androidx.lifecycle.MutableLiveData
import com.haotsang.wanandroid.base.BaseViewModel
import com.haotsang.wanandroid.model.store.UserInfoStore

class RegisterViewModel : BaseViewModel() {

    private val registerRepository by lazy { RegisterRepository() }
    val submitting = MutableLiveData<Boolean>()
    val registerResult = MutableLiveData<Boolean>()

    fun register(account: String, password: String, confirmPassword: String) {
        launch(
            block = {
                submitting.value = true
                val userInfo = registerRepository.register(account, password, confirmPassword)
                UserInfoStore.setUserInfo(userInfo)
                userStatusInfo.postValue(userInfo)

                registerResult.value = true
                submitting.value = false
            },
            error = {
                registerResult.value = false
                submitting.value = false
            }
        )
    }
}