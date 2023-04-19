package com.haotsang.wanandroid.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haotsang.wanandroid.model.bean.Banner
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val repository by lazy { HomeRepository() }

    private val banners: MutableLiveData<List<Banner>?> by lazy {
        MutableLiveData<List<Banner>?>()
    }

    fun getBannerObserve(): LiveData<List<Banner>?> {
        return banners
    }

    fun getBannerData() {
        viewModelScope.launch {
            banners.postValue(repository.getBanners())
        }
    }


}