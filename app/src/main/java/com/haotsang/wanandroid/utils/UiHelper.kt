package com.haotsang.wanandroid.utils

import android.app.Activity
import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.haotsang.wanandroid.utils.ext.getAttrData

object UiHelper {

    /**
     * Return the random color.
     *
     * @param supportAlpha True to support alpha, false otherwise.
     * @return the random color
     */
    fun getRandomColor(supportAlpha: Boolean): Int {
        val high = if (supportAlpha) (Math.random() * 0x100).toInt() shl 24 else -0x1000000
        return high or (Math.random() * 0x1000000).toInt()
    }

    fun setFullscreenCompat(activity: Activity, fullscreen: Boolean) {
        val window = activity.window
        val decorView = window.decorView
        if (fullscreen) {
            WindowInsetsControllerCompat(window, decorView).let { controller ->
                controller.hide(WindowInsetsCompat.Type.systemBars())
                controller.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        } else {
            WindowInsetsControllerCompat(window, decorView).show(WindowInsetsCompat.Type.systemBars())
        }
    }

    fun setSystemBarTransparent(activity: AppCompatActivity) {
        val window = activity.window
        val view = window.decorView
        val flag = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
        view.systemUiVisibility = view.systemUiVisibility or flag
        window.apply {
            clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            statusBarColor = Color.TRANSPARENT
            navigationBarColor = Color.TRANSPARENT
        }
    }


    fun setFullScreenWindowLayoutInDisplayCutout(activity: Activity, isCutout: Boolean) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            // 设置窗口占用刘海区
            val lp = activity.window.attributes
            lp.layoutInDisplayCutoutMode =
                if (isCutout) WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES else
                    WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_DEFAULT
            activity.window.attributes = lp
        }
    }


    fun initSystemBarColor(activity: Activity?) {
        val window = activity?.window ?: return

        val statusBarColor: Int
        val navigationBarColor: Int
        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q -> {
                window.isStatusBarContrastEnforced = false
                window.isNavigationBarContrastEnforced = false

                statusBarColor = Color.TRANSPARENT
                navigationBarColor = Color.TRANSPARENT
            }
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.P -> {
                window.navigationBarDividerColor = Color.TRANSPARENT

                statusBarColor = Color.TRANSPARENT
                navigationBarColor = Color.TRANSPARENT
            }
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.O -> {
                statusBarColor = Color.TRANSPARENT
                navigationBarColor = activity.getAttrData(com.google.android.material.R.attr.colorPrimary)
            }
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.M -> {
                statusBarColor = Color.TRANSPARENT
                navigationBarColor = activity.getAttrData(com.google.android.material.R.attr.colorPrimary)
            }
            else -> {
                statusBarColor = activity.getAttrData(com.google.android.material.R.attr.colorPrimary)
                navigationBarColor = activity.getAttrData(com.google.android.material.R.attr.colorPrimary)
            }
        }

        window.statusBarColor = statusBarColor
        window.navigationBarColor = navigationBarColor
    }


    fun initSystemBarsColor(activity: Activity) {
        val decorView = activity.window.decorView
        val controller = WindowInsetsControllerCompat(activity.window, decorView)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            controller.isAppearanceLightStatusBars = false
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            controller.isAppearanceLightNavigationBars = false
        }
    }

}