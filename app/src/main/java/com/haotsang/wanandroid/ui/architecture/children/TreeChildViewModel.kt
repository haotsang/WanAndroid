package com.haotsang.wanandroid.ui.architecture.children

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.haotsang.wanandroid.base.BaseViewModel
import com.haotsang.wanandroid.common.loadmore.LoadMoreStatus
import com.haotsang.wanandroid.model.bean.Article
import kotlinx.coroutines.Job

class TreeChildViewModel : BaseViewModel() {
    companion object {
        const val INITIAL_PAGE = 0
    }

    private var page = INITIAL_PAGE
    private var id: Int = -1
    private var refreshJob: Job? = null

    val loadMoreStatus = MutableLiveData<LoadMoreStatus>()
    val refreshStatus = MutableLiveData<Boolean>()
    val reloadStatus = MutableLiveData<Boolean>()

    private val repository by lazy { TreeChildRepository() }

    private val articleList: MutableLiveData<List<Article>?> by lazy {
        MutableLiveData<List<Article>?>()
    }

    fun getArticleList(): LiveData<List<Article>?> {
        return articleList
    }


    fun refreshArticleList(cid: Int) {
        if (cid != id) {
            cancelJob(refreshJob)
            id = cid
            articleList.value = mutableListOf()
        }
        refreshJob = launch(
            block = {
                refreshStatus.value = true
                reloadStatus.value = false
                val pagination = repository.getArticleListByCid(INITIAL_PAGE, cid)
                page = pagination.curPage
                articleList.value = pagination.datas
                refreshStatus.value = false
            },
            error = {
                refreshStatus.value = false
                reloadStatus.value = articleList.value?.isEmpty()
            }
        )
    }

    fun loadMoreArticleList(cid: Int) {
        launch(
            block = {
                loadMoreStatus.value = LoadMoreStatus.LOADING
                val pagination = repository.getArticleListByCid(page, cid)
                page = pagination.curPage
                articleList.value = articleList.value!! + (pagination.datas)

                loadMoreStatus.value = if (pagination.offset >= pagination.total) {
                    LoadMoreStatus.END
                } else {
                    LoadMoreStatus.COMPLETED
                }
            },
            error = {
                loadMoreStatus.value = LoadMoreStatus.ERROR
            }
        )
    }

}