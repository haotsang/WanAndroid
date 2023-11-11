package com.haotsang.wanandroid.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.haotsang.wanandroid.common.dialog.ProgressDialogFragment

/**
 *  示例
 *  ```
 *  class XXXActivity : BaseActivity<XXXBinding>(XXXBinding::inflate) {
 *  }
 *  ```
 */

abstract class BaseActivity<VB : ViewBinding>(private val inflate: (LayoutInflater) -> VB) : AppCompatActivity() {

    protected open lateinit var mBinding: VB

    private lateinit var progressDialogFragment: ProgressDialogFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = inflate(layoutInflater)
        setContentView(mBinding.root)
        initialize()
    }

    /**
     * 命名与子类要区分，否则会先调用BaseActivity中同名方法，再调用BaseVmActivity中同名方法，可能会出现调用顺序导致的错误
     */
    open fun initialize() {}


    /**
     * 显示加载(转圈)对话框
     */
    fun showProgressDialog(@StringRes message: Int) {
        if (!this::progressDialogFragment.isInitialized) {
            progressDialogFragment = ProgressDialogFragment.newInstance()
        }
        if (!progressDialogFragment.isAdded) {
            progressDialogFragment.show(supportFragmentManager, message, false)
        }
    }

    /**
     * 隐藏加载(转圈)对话框
     */
    fun dismissProgressDialog() {
        if (this::progressDialogFragment.isInitialized && progressDialogFragment.isVisible) {
            progressDialogFragment.dismissAllowingStateLoss()
        }
    }

}