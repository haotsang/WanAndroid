package com.haotsang.wanandroid.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri

object FileHelper {

    @SuppressLint("WrongConstant")
    fun takeReadPermissions(context: Context, uri: Uri?) {
        if (uri != null) {
            val takeFlags = Intent.FLAG_GRANT_READ_URI_PERMISSION
            context.contentResolver.takePersistableUriPermission(uri, takeFlags)
        }
    }
}