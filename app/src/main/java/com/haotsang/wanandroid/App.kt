package com.haotsang.wanandroid

import android.app.Application
import kotlin.properties.Delegates

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {

        var instance: App by Delegates.notNull()

        //双重校验锁实现单例
        val instance2: App by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            App()
        }
    }
}