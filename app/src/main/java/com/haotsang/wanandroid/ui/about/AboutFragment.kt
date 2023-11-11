package com.haotsang.wanandroid.ui.about


import android.content.Intent
import android.content.res.AssetManager
import android.graphics.Paint
import android.graphics.Typeface
import com.haotsang.wanandroid.R
import com.haotsang.wanandroid.base.BaseFragment
import com.haotsang.wanandroid.databinding.AboutFragmentBinding

class AboutFragment : BaseFragment<AboutFragmentBinding>(AboutFragmentBinding::inflate) {

    override fun initialize() {
        super.initialize()
        mBinding?.toolbar?.title = "${getString(R.string.app_name)}  V1.0"
        mBinding?.toolbar?.setNavigationOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }


        //添加下划线
        mBinding?.tvAuthor?.paint?.flags = Paint.UNDERLINE_TEXT_FLAG

        mBinding?.tvGithub?.paint?.flags = Paint.UNDERLINE_TEXT_FLAG
        mBinding?.tvGithub?.setOnClickListener {
        }


        //获取AssetManager
        val assets = requireContext().assets as AssetManager
        //根据路径得到字体
        val typeface = Typeface.createFromAsset(assets, "fonts/mononoki-Regular.ttf")
        //设置给TextView
        mBinding?.tvAuthor?.typeface = typeface
        mBinding?.tvGithub?.typeface = typeface
        mBinding?.tvApi?.typeface = typeface
        mBinding?.tvLibrary?.typeface = typeface
    }
}