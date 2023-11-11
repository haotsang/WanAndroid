package com.haotsang.wanandroid.ui.architecture.children

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.haotsang.wanandroid.base.BaseViewModel
import com.haotsang.wanandroid.model.bean.Article

class TreeChildViewModel : BaseViewModel() {

    private val repository by lazy { TreeChildRepository() }

    private val articleList: MutableLiveData<List<Article>?> by lazy {
        MutableLiveData<List<Article>?>()
    }

    fun getArticleList(): LiveData<List<Article>?> {
        return articleList
    }

    fun getTreeChild(page: Int, cid: Int) {
        launch(
            block = {
                articleList.postValue(repository.getTreeChild(page, cid))
            },
            error = {

            }
        )
    }

}