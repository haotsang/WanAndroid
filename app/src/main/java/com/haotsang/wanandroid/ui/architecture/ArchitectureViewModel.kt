package com.haotsang.wanandroid.ui.architecture

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haotsang.wanandroid.model.bean.Category
import kotlinx.coroutines.launch

class ArchitectureViewModel : ViewModel() {

    private val repository by lazy { ArchitectureRepository() }


    private val architecture: MutableLiveData<List<Category>?> by lazy {
        MutableLiveData<List<Category>?>()
    }


    fun getArchitectureObserve(): LiveData<List<Category>?> {
        return architecture
    }

    fun getArchitectureData() {
        viewModelScope.launch {
            architecture.postValue(repository.getArchitecture())
        }
    }


}