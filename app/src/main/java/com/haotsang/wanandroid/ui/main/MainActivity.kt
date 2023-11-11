package com.haotsang.wanandroid.ui.main

import android.animation.ObjectAnimator
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.animation.AnticipateInterpolator
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.signature.ObjectKey
import com.haotsang.wanandroid.base.BaseVmActivity
import com.haotsang.wanandroid.databinding.MainActivityBinding
import com.haotsang.wanandroid.utils.InsetsWithKeyboardCallback
import com.haotsang.wanandroid.utils.UiHelper

class MainActivity :
    BaseVmActivity<MainActivityBinding, MainViewModel>(MainActivityBinding::inflate) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        initSplashScreen()

        val insetsWithKeyboardCallback =
            InsetsWithKeyboardCallback(window) { height, ime ->
        }
        ViewCompat.setOnApplyWindowInsetsListener(mBinding.mainContent, insetsWithKeyboardCallback)

//        ViewCompat.setOnApplyWindowInsetsListener(mBinding.mainContent) { view, insets ->
//            var consumed = false
//
//            (view as ViewGroup).forEach { child ->
//                // 将 insets 分发给孩子
//                val childResult = ViewCompat.dispatchApplyWindowInsets(child, insets)
//                // 如果孩子消费了 insets，记录它
//                if (childResult.isConsumed) {
//                    consumed = true
//                }
//            }
//
//            // 如果任何孩子消费了 insets，则返回一个适当的值
//            if (consumed) WindowInsetsCompat.CONSUMED else insets
//        }


        if (savedInstanceState == null) {
            val mainFragment = MainFragment()
            supportFragmentManager.beginTransaction()
                .replace(mBinding.mainContent.id, mainFragment, null)
                .commitAllowingStateLoss()
        }
    }

    override fun viewModelClass(): Class<MainViewModel> = MainViewModel::class.java

    override fun observe() {
        super.observe()
        mViewModel.homeBgObserver.observe(this) { uri ->
            Glide.with(this)
                .load(uri)
                .apply(com.bumptech.glide.request.RequestOptions()
                    .centerCrop()
                    .placeholder(null)
                    .error(null)
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                )
                .transition(DrawableTransitionOptions.withCrossFade())
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean,
                    ): Boolean {
                        return false
                    }
                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean,
                    ): Boolean {
                        return false
                    }
                })
                .into(mBinding.mainBg)
        }
    }

    fun switchFragmentPage(targetFragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
//        currentPage?.let { transaction.hide(it) }
        supportFragmentManager.fragments.forEach {
            transaction.hide(it)
        }
        if (targetFragment.isAdded && !targetFragment.isDetached) {
            transaction.show(targetFragment)
        } else {
            transaction.add(mBinding.mainContent.id, targetFragment, null)
        }
        transaction.addToBackStack(null).commitAllowingStateLoss()
    }

    private fun initSplashScreen() {
        val splashScreen = installSplashScreen()
        splashScreen.setKeepOnScreenCondition {
            mViewModel.isLoading.value
        }

        splashScreen.setOnExitAnimationListener { splashScreenView ->
            // Create your custom animation.
            val slideUp = ObjectAnimator.ofFloat(
                splashScreenView.view,
                View.TRANSLATION_Y,
                0f,
                -(splashScreenView.view.height / 3).toFloat()
            )
            slideUp.interpolator = AnticipateInterpolator()
            slideUp.duration = 200L
            slideUp.doOnEnd {
                splashScreenView.remove()
                initSystemBars()
            }
            slideUp.start()
        }
    }


    private fun initSystemBars() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        UiHelper.setFullScreenWindowLayoutInDisplayCutout(this, true)
        UiHelper.setSystemBarTransparent(this)
        UiHelper.initSystemBarsColor(this)
    }
}