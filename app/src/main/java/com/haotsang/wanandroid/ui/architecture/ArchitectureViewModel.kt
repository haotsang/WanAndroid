package com.haotsang.wanandroid.ui.architecture

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.haotsang.wanandroid.base.BaseViewModel
import com.haotsang.wanandroid.model.bean.Category

class ArchitectureViewModel : BaseViewModel() {

    val loadingStatus = MutableLiveData<Boolean>()
    val reloadStatus = MutableLiveData<Boolean>()

    private val repository by lazy { ArchitectureRepository() }

    private val categories: MutableLiveData<List<Category>?> by lazy {
        MutableLiveData<List<Category>?>()
    }


    fun getCategoriesObserve(): LiveData<List<Category>?> {
        return categories
    }

    fun getArticleCategory() {
        launch(
            block = {
                loadingStatus.value = true
                reloadStatus.value = false
                categories.postValue(repository.getArticleCategories())
                loadingStatus.value = false
            },
            error = {
                loadingStatus.value = false
                reloadStatus.value = true
            }
        )
    }


}