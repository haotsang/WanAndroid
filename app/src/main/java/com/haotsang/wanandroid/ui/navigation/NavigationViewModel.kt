package com.haotsang.wanandroid.ui.navigation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haotsang.wanandroid.model.bean.Navigation
import kotlinx.coroutines.launch

class NavigationViewModel : ViewModel() {

    private val repository by lazy { NavigationRepository() }

    private val navigation: MutableLiveData<List<Navigation>?> by lazy {
        MutableLiveData<List<Navigation>?>()
    }


    fun getNavigationObserve(): LiveData<List<Navigation>?> {
        return navigation
    }

    fun getNavigationData() {
        viewModelScope.launch {
            navigation.postValue(repository.getNavigation())
        }
    }

}