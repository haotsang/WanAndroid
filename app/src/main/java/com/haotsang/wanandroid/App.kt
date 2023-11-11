package com.haotsang.wanandroid

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import com.haotsang.wanandroid.common.loadmore.LoadMoreHelper
import com.haotsang.wanandroid.ui.CrashActivity
import com.haotsang.wanandroid.utils.NeverCrash
import com.haotsang.wanandroid.utils.ext.isMainProcess
import kotlin.properties.Delegates


class App : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this


        if (isMainProcess(this)) {
            LoadMoreHelper.init()
            NeverCrash.init { t, e ->
                CrashActivity.start(this, e)
                System.exit(1)
            }
        }
    }


    /**
     * Application 保存的ViewModel 同一Application获取到的对应ViewModel为同一个
     *
     * @return
     */
    fun of(): ViewModelProvider {
        val factory: ViewModelProvider.AndroidViewModelFactory =
            ViewModelProvider.AndroidViewModelFactory.getInstance(instance)
        return ViewModelProvider(sViewModelStore, factory)
    }

    companion object {
        /**
         * 用于存储ViewModel
         */
        private val sViewModelStore = ViewModelStore()


        var instance: App by Delegates.notNull()

        //双重校验锁实现单例
        val instance2: App by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            App()
        }
    }
}