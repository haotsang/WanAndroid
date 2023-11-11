package com.haotsang.wanandroid.ui.navigation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.haotsang.wanandroid.base.BaseViewModel
import com.haotsang.wanandroid.model.bean.Navigation

class NavigationViewModel : BaseViewModel() {

    private val repository by lazy { NavigationRepository() }

    private val navigation: MutableLiveData<List<Navigation>?> by lazy {
        MutableLiveData<List<Navigation>?>()
    }


    fun getNavigationObserve(): LiveData<List<Navigation>?> {
        return navigation
    }

    fun getNavigationData() {
        launch(
            block = {
                navigation.postValue(repository.getNavigation())
            }
        )
    }

}