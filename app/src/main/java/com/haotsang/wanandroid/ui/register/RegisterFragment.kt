package com.haotsang.wanandroid.ui.register

import com.haotsang.wanandroid.R
import com.haotsang.wanandroid.base.BaseVmFragment
import com.haotsang.wanandroid.databinding.RegisterFragmentBinding
import com.haotsang.wanandroid.ui.main.MainActivity

class RegisterFragment :
    BaseVmFragment<RegisterFragmentBinding, RegisterViewModel>(RegisterFragmentBinding::inflate) {

    override fun viewModelClass(): Class<RegisterViewModel> = RegisterViewModel::class.java

    override fun initView() {
        mBinding?.toolbar?.setNavigationOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        mBinding?.btnRegister?.setOnClickListener {
            mBinding?.tilAccount?.error = ""
            mBinding?.tilPassword?.error = ""
            mBinding?.tilConfirmPssword?.error = ""

            val account = mBinding?.tietAccount?.text.toString()
            val password = mBinding?.tietPassword?.text.toString()
            val confirmPassword = mBinding?.tietConfirmPssword?.text.toString()

            when {
                account.isEmpty() -> mBinding?.tilAccount?.error = getString(R.string.account_can_not_be_empty)
                account.length < 3 -> mBinding?.tilAccount?.error =
                    getString(R.string.account_length_over_three)
                password.isEmpty() -> mBinding?.tilPassword?.error =
                    getString(R.string.password_can_not_be_empty)
                password.length < 6 -> mBinding?.tilPassword?.error =
                    getString(R.string.password_length_over_six)
                confirmPassword.isEmpty() -> mBinding?.tilConfirmPssword?.error =
                    getString(R.string.confirm_password_can_not_be_empty)
                password != confirmPassword -> mBinding?.tilConfirmPssword?.error =
                    getString(R.string.two_password_are_inconsistent)
                else -> mViewModel.register(account, password, confirmPassword)
            }
        }
    }

    override fun observe() {
        super.observe()
        mViewModel.run {
            submitting.observe(viewLifecycleOwner) {
                if (it) (requireActivity() as MainActivity).showProgressDialog(R.string.registerring)
                else (requireActivity() as MainActivity).dismissProgressDialog()
            }
            registerResult.observe(viewLifecycleOwner) {
                if (it) {
                    requireActivity().supportFragmentManager.popBackStack()
                }
            }
        }
    }
}