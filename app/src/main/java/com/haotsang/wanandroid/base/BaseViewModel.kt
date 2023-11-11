package com.haotsang.wanandroid.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonParseException
import com.haotsang.wanandroid.App
import com.haotsang.wanandroid.R
import com.haotsang.wanandroid.model.api.ApiException
import com.haotsang.wanandroid.model.api.RetrofitClient
import com.haotsang.wanandroid.model.bean.UserInfo
import com.haotsang.wanandroid.model.store.UserInfoStore
import com.haotsang.wanandroid.utils.ext.showToast
import com.squareup.moshi.JsonEncodingException
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.net.ssl.SSLHandshakeException

typealias Block<T> = suspend (CoroutineScope) -> T
typealias Error = suspend (Exception) -> Unit
typealias Cancel = suspend (Exception) -> Unit

open class BaseViewModel : ViewModel() {

    val userStatusInfo: MutableLiveData<UserInfo?> = MutableLiveData()

    /**
     * 创建并执行协程 Coroutines
     * @param block 协程中执行
     * @param error 错误时执行
     * @param cancel 取消时执行
     * @param showErrorToast 是否弹出错误吐司
     * @return Job API 文档 https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-job/index.html
     *
     * CoroutineScope.launch 函数返回的是一个 Job 对象，代表一个异步的任务。
     * viewModelScope 也是继承 CoroutineScope的
     * Job 具有生命周期并且可以取消。
     * Job 还可以有层级关系，一个Job可以包含多个子Job，当父Job被取消后，所有的子Job也会被自动取消；
     * 当子Job被取消或者出现异常后父Job也会被取消。
     */
    protected fun launch(
        block: Block<Unit>,
        error: Error? = null,
        cancel: Cancel? = null,
        showErrorToast: Boolean = true
    ): Job {
        return viewModelScope.launch {
            try {
                block.invoke(this)
            } catch (e: Exception) {
                when (e) {
                    is CancellationException -> {
                        cancel?.invoke(e)
                    }
                    else -> {
                        onError(e, showErrorToast)
                        error?.invoke(e)
                    }
                }
            }
        }
    }


    /**
     * 创建并执行协程
     * @param block 协程中执行
     * @return Deferred<T> 继承自 Job 额外多3个方法
     */
    protected fun <T> async(block: Block<T>): Deferred<T> {
        return viewModelScope.async { block.invoke(this) }
    }

    /**
     * 取消协程 会抛出CancellationException
     * @param job 协程job
     */
    protected fun cancelJob(job: Job?) {
        if (job != null && job.isActive && !job.isCompleted && !job.isCancelled) {
            job.cancel()
        }
    }

    /**
     * 统一处理错误
     * @param e 异常
     * @param showErrorToast 是否显示错误吐司
     */
    private fun onError(e: Exception, showErrorToast: Boolean) {
        println("---------------start-----------------")
        println(e)
        println("---------------end-----------------")

        when (e) {
            is ApiException -> {
                when (e.code) {
                    -1001 -> {
                        // 登录失效，清除用户信息、清除cookie/token
                        UserInfoStore.clearUserInfo()
                        RetrofitClient.clearCookie()
                        userStatusInfo.postValue(null)
                    }
                    // 其他api错误
                    -1 -> if (showErrorToast) App.instance.showToast("api error -1:" + e.message)
                    // 其他错误
                    else -> if (showErrorToast) App.instance.showToast("api error:" + e.message)
                }
            }
            // 网络请求失败
            is ConnectException, is SocketTimeoutException, is UnknownHostException, is HttpException, is SSLHandshakeException -> {
                if (showErrorToast) App.instance.showToast(R.string.network_request_failed)
            }
            // 数据解析错误
            is JsonParseException, is JsonEncodingException -> {
                if (showErrorToast) App.instance.showToast(R.string.api_data_parse_error)
            }
            // 其他错误
            else -> {
                if (showErrorToast) App.instance.showToast("error:" + e.message)
            }

        }
    }

}