package com.haotsang.wanandroid.ui.profile

import com.haotsang.wanandroid.App
import com.haotsang.wanandroid.R
import com.haotsang.wanandroid.base.BaseFragment
import com.haotsang.wanandroid.base.BaseVmFragment
import com.haotsang.wanandroid.databinding.ProfileFragmentBinding
import com.haotsang.wanandroid.model.store.UserInfoStore
import com.haotsang.wanandroid.model.store.isLogin
import com.haotsang.wanandroid.ui.about.AboutFragment
import com.haotsang.wanandroid.ui.login.LoginFragment
import com.haotsang.wanandroid.ui.login.LoginViewModel
import com.haotsang.wanandroid.ui.main.MainActivity
import com.haotsang.wanandroid.ui.points.mine.MinePointsFragment
import com.haotsang.wanandroid.ui.register.RegisterViewModel
import com.haotsang.wanandroid.ui.settngs.SettingFragment
import com.haotsang.wanandroid.utils.ext.showToast

class ProfileFragment : BaseVmFragment<ProfileFragmentBinding, ProfileViewModel>(ProfileFragmentBinding::inflate) {

    private val loginViewModel = App.instance.of()[LoginViewModel::class.java]
    private val registerViewModel = App.instance.of()[RegisterViewModel::class.java]

    override fun viewModelClass(): Class<ProfileViewModel> = ProfileViewModel::class.java

    override fun initView() {
        mBinding?.userAvatar?.setOnClickListener {
            if (isLogin()) {

            } else {
                (requireActivity() as MainActivity).switchFragmentPage(LoginFragment())
            }
        }


        mBinding?.itemContainer?.apply {
            profileItemAbout.setOnClickListener { (requireActivity() as MainActivity).switchFragmentPage(AboutFragment()) }
        }


        mBinding?.userCoinCount?.setOnClickListener {
            if (isLogin()) {
                (requireActivity() as MainActivity).switchFragmentPage(MinePointsFragment())
            }
        }
        mBinding?.settingFabIcon?.setOnClickListener {
            (requireActivity() as MainActivity).switchFragmentPage(SettingFragment())
        }

        updateUi()
    }

    override fun observe() {
        super.observe()
        loginViewModel.loginResult.observe(this) {
            if (it) updateUi()
        }
        registerViewModel.registerResult.observe(this) {
            if (it) updateUi()
        }

        mViewModel.userStatusInfo.observe(this) {
            println("hirp---"+it.toString())
        }


    }

    private fun updateUi() {
        val isLogin = isLogin()
        val userInfo = UserInfoStore.getUserInfo()

        if (isLogin && userInfo != null) {
            mBinding?.userName?.text = userInfo.nickname
            mBinding?.userId?.text = "ID: ${userInfo.id}"
        }

    }
}