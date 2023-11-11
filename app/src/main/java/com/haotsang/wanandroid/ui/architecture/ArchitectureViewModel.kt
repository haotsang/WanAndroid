package com.haotsang.wanandroid.ui.architecture

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.haotsang.wanandroid.base.BaseViewModel
import com.haotsang.wanandroid.model.bean.Category

class ArchitectureViewModel : BaseViewModel() {

    private val repository by lazy { ArchitectureRepository() }


    private val architecture: MutableLiveData<List<Category>?> by lazy {
        MutableLiveData<List<Category>?>()
    }


    fun getArchitectureObserve(): LiveData<List<Category>?> {
        return architecture
    }

    fun getArchitectureData() {
        launch(
            block = {
                architecture.postValue(repository.getArchitecture())
            }
        )
    }


}