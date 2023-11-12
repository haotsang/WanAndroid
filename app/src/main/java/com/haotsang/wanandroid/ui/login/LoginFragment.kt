package com.haotsang.wanandroid.ui.login

import com.haotsang.wanandroid.R
import com.haotsang.wanandroid.base.BaseVmFragment
import com.haotsang.wanandroid.databinding.LoginFragmentBinding
import com.haotsang.wanandroid.ui.main.MainActivity
import com.haotsang.wanandroid.ui.register.RegisterFragment

class LoginFragment :
    BaseVmFragment<LoginFragmentBinding, LoginViewModel>(LoginFragmentBinding::inflate) {

    override fun viewModelClass(): Class<LoginViewModel> = LoginViewModel::class.java

    override fun initView() {
        mBinding?.toolbar?.setNavigationOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
        mBinding?.tvGoRegister?.setOnClickListener {
            (requireActivity() as MainActivity).switchFragmentPage(this, RegisterFragment())
        }

        mBinding?.btnLogin?.setOnClickListener {
            mBinding?.tilAccount?.error = ""
            mBinding?.tilPassword?.error = ""
            val account = mBinding?.tietAccount?.text.toString()
            val password = mBinding?.tietPassword?.text.toString()
            when {
                account.isEmpty() -> mBinding?.tilAccount?.error = getString(R.string.account_can_not_be_empty)
                password.isEmpty() -> mBinding?.tilPassword?.error =
                    getString(R.string.password_can_not_be_empty)
                else -> mViewModel.login(account, password)
            }
        }
    }

    override fun observe() {
        super.observe()
        mViewModel.run {
            submitting.observe(viewLifecycleOwner) {
                if (it) (requireActivity() as MainActivity).showProgressDialog(R.string.logging_in)
                else (requireActivity() as MainActivity).dismissProgressDialog()
            }
            loginResult.observe(viewLifecycleOwner) {
                if (it) {
                    requireActivity().supportFragmentManager.popBackStack()
                }
            }
        }
    }

    override fun initData() {
        super.initData()

    }

}