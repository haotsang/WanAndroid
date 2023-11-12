package com.haotsang.wanandroid.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding

abstract class BaseVmFragment<VB : ViewBinding, VM : BaseViewModel>(inflate: (LayoutInflater) -> VB) : BaseFragment<VB>(inflate) {

    protected open lateinit var mViewModel: VM

    private var lazyLoaded = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        observe()
        initView()
        initData()
        setListener()
    }


    /**
     * 初始化ViewModel
     */
    private fun initViewModel() {
        mViewModel = ViewModelProvider(this)[viewModelClass()]
    }

    /**
     * 获取ViewModel的class
     */
    abstract fun viewModelClass(): Class<VM>

    /**
     * 订阅，有逻辑的话，复写的时候super不要去掉
     */
    open fun observe() {}

    /**
     * 初始化view相关
     */
    abstract fun initView()

    /**
     * 初始化data相关
     */
    open fun initData() {}


    /**
     * 设置监听
     */
    open fun setListener() {}


    override fun onResume() {
        super.onResume()
        // 实现懒加载
        if (!lazyLoaded) {
            lazyLoadData()
            lazyLoaded = true
        }
    }

    /**
     * 懒加载数据
     */
    open fun lazyLoadData() {
        // Override if need
    }
}