package com.haotsang.wanandroid.ui.discovery.question

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.haotsang.wanandroid.base.BaseViewModel
import com.haotsang.wanandroid.common.loadmore.LoadMoreStatus
import com.haotsang.wanandroid.model.bean.Article

class QuestionViewModel : BaseViewModel() {

    companion object {
        const val INITIAL_PAGE = 0
    }

    private var page = INITIAL_PAGE
    val loadMoreStatus = MutableLiveData<LoadMoreStatus>()
    val refreshStatus = MutableLiveData<Boolean>()
    val reloadStatus = MutableLiveData<Boolean>()

    private val repository by lazy { QuestionRepository() }

    private val questionLiveData = MutableLiveData<List<Article>>().also {
        it.value = null
    }

    fun getQuestionObserve(): LiveData<List<Article>> {
        return questionLiveData
    }


    fun refreshQuestionList() {
        refreshStatus.value = true
        reloadStatus.value = false

        launch(block = {
            loadMoreStatus.value = LoadMoreStatus.LOADING

            val questionList = repository.getQuestionList(INITIAL_PAGE)

            questionLiveData.postValue(questionList.datas)
            refreshStatus.value = false
        }, error = {
            refreshStatus.value = false
            reloadStatus.value = page == INITIAL_PAGE
        })
    }

    fun loadMoreQuestionList() {
        launch(
            block = {
                loadMoreStatus.value = LoadMoreStatus.LOADING

                val pagination = repository.getQuestionList(page)
                page = pagination.curPage
                questionLiveData.value = questionLiveData.value!!+(pagination.datas)

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