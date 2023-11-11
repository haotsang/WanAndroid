package com.haotsang.wanandroid.ui.login

import androidx.lifecycle.MutableLiveData
import com.haotsang.wanandroid.base.BaseViewModel
import com.haotsang.wanandroid.model.store.UserInfoStore

class LoginViewModel : BaseViewModel() {

    private val loginRepository by lazy { LoginRepository() }
    val submitting = MutableLiveData<Boolean>()
    val loginResult = MutableLiveData<Boolean>()


    fun login(account: String, password: String) {
        launch(
            block = {
                submitting.value = true
                val userInfo = loginRepository.login(account, password)
                UserInfoStore.setUserInfo(userInfo)
                userStatusInfo.postValue(userInfo)

                submitting.value = false
                loginResult.value = true
            },
            error = {
                submitting.value = false
                loginResult.value = false
            }
        )
    }

}