package com.haotsang.wanandroid.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.haotsang.wanandroid.base.BaseViewModel
import com.haotsang.wanandroid.model.bean.HotWord

class SearchViewModel : BaseViewModel() {

    private val searchRepository by lazy { SearchRepository() }

    private val hotWords: MutableLiveData<List<HotWord>?> by lazy {
        MutableLiveData<List<HotWord>?>()
    }
    private val history: MutableLiveData<List<String>?> by lazy {
        MutableLiveData<List<String>?>()
    }

    fun getHotWordsObserve(): LiveData<List<HotWord>?> {
        return hotWords
    }

    fun getHistoryObserve(): LiveData<List<String>?> {
        return history
    }

    fun getHotWords() {
        launch(block = { hotWords.postValue(searchRepository.getHotWords()) })
    }

    fun getSearchHistory() {
        launch(block = { history.postValue(searchRepository.getSearchHistory()) })
    }


}