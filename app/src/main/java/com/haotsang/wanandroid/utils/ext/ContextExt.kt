package com.haotsang.wanandroid.utils.ext

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.util.TypedValue
import android.widget.Toast
import androidx.annotation.AttrRes
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat

val Context.activity: Activity?
    get() {
        var context = this
        while (true) {
            when (context) {
                is Activity -> return context
                is ContextWrapper -> context = context.baseContext
                else -> return null
            }
        }
    }

fun Context.getAttrData(@AttrRes attr: Int): Int {
    val typedValue = TypedValue()
    synchronized(typedValue) {
        theme.resolveAttribute(attr, typedValue, true)
        return typedValue.data
    }
}

fun Context.toColor(@ColorRes id: Int): Int {
    return ContextCompat.getColor(this, id)
}

fun Context.showToast(message: CharSequence) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.showToast(@StringRes message: Int) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}