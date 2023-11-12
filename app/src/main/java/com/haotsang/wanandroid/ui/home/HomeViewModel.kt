package com.haotsang.wanandroid.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.haotsang.wanandroid.base.BaseViewModel
import com.haotsang.wanandroid.common.loadmore.LoadMoreStatus
import com.haotsang.wanandroid.model.bean.Article
import com.haotsang.wanandroid.model.bean.Banner

class HomeViewModel : BaseViewModel() {

    companion object {
        const val INITIAL_PAGE = 0
    }

    private var page = INITIAL_PAGE
    val loadMoreStatus = MutableLiveData<LoadMoreStatus>()
    val refreshStatus = MutableLiveData<Boolean>()
    val reloadStatus = MutableLiveData<Boolean>()

    private val repository by lazy { HomeRepository() }

    private val banners: MutableLiveData<List<Banner>?> by lazy {
        MutableLiveData<List<Banner>?>()
    }

    private val articleList: MutableLiveData<List<Article>?> by lazy {
        MutableLiveData<List<Article>?>()
    }

    fun getBannerObserve(): LiveData<List<Banner>?> {
        return banners
    }

    fun getArticleObserve(): LiveData<List<Article>?> {
        return articleList
    }

    fun getBannerData() {
        launch(
            block = {
                banners.postValue(repository.getBanners())
            }
        )
    }

    fun getArticleData() {
        refreshStatus.value = true
        reloadStatus.value = false

        launch(block = {
            loadMoreStatus.value = LoadMoreStatus.LOADING

            val topArticle = repository.getTopArticleList().onEach { it.top = true }
            val paginationList = repository.getArticleList(INITIAL_PAGE)

            page = paginationList.curPage
            articleList.postValue(topArticle + paginationList.datas)
            refreshStatus.value = false
        }, error = {
            refreshStatus.value = false
            reloadStatus.value = page == INITIAL_PAGE
        })
    }

    fun loadMoreArticleList() {
        launch(
            block = {
                loadMoreStatus.value = LoadMoreStatus.LOADING

                val pagination = repository.getArticleList(page)
                page = pagination.curPage
                articleList.value = articleList.value!!+(pagination.datas)

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