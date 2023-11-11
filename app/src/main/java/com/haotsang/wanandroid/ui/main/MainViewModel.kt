package com.haotsang.wanandroid.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haotsang.wanandroid.base.BaseViewModel
import com.haotsang.wanandroid.model.bean.Banner
import com.haotsang.wanandroid.utils.DataStoreUtils
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MainViewModel : BaseViewModel() {

    val homeBgObserver: MutableLiveData<String?> by lazy {
        MutableLiveData<String?>()
    }

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    init {
        homeBgObserver.postValue(DataStoreUtils.getData(DataStoreUtils.ds_home_bg))

        viewModelScope.launch {
            delay(1500)
            _isLoading.value = false
        }
    }
}