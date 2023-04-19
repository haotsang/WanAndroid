package com.haotsang.wanandroid.utils

import android.content.Context
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent

object BrowserUtils {

    fun openInBrowser(context: Context, url: String) {
        val builder = CustomTabsIntent.Builder()
        val customTabsIntent :CustomTabsIntent  = builder.build();
        customTabsIntent.launchUrl(context, Uri.parse(url))
    }
}