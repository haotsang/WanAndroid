package com.haotsang.wanandroid.ui.question

import android.view.LayoutInflater
import android.view.ViewGroup
import com.haotsang.wanandroid.base.BaseViewBindingFragment
import com.haotsang.wanandroid.databinding.QuestionFragmentBinding

class QuestionFragment : BaseViewBindingFragment<QuestionFragmentBinding>() {
    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): QuestionFragmentBinding {
        return QuestionFragmentBinding.inflate(inflater, container, false)
    }

    override fun observe() {

    }

    override fun initView() {

    }

    override fun initData() {

    }

}