package com.haotsang.wanandroid.utils.ext

import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.marginBottom
import androidx.core.view.marginEnd
import androidx.core.view.marginStart
import androidx.core.view.marginTop
import androidx.core.view.updateLayoutParams
import androidx.core.view.updateMarginsRelative
import androidx.core.view.updatePaddingRelative
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2

fun View.addSystemWindowInsetToPadding(
    left: Boolean = false,
    top: Boolean = false,
    right: Boolean = false,
    bottom: Boolean = false
) {
    val (initialLeft, initialTop, initialRight, initialBottom) =
        listOf(paddingStart, paddingTop, paddingEnd, paddingBottom)

    ViewCompat.setOnApplyWindowInsetsListener(this) { view, insets ->
        val typeInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars())
        view.updatePaddingRelative(
            start = initialLeft + (if (left) typeInsets.left else 0),
            top = initialTop + (if (top) typeInsets.top else 0),
            end = initialRight + (if (right) typeInsets.right else 0),
            bottom = initialBottom + (if (bottom) typeInsets.bottom else 0)
        )

        insets
    }
}

fun View.addSystemWindowInsetToMargin(
    left: Boolean = false,
    top: Boolean = false,
    right: Boolean = false,
    bottom: Boolean = false
) {
    val (initialLeft, initialTop, initialRight, initialBottom) =
        listOf(marginStart, marginTop, marginEnd, marginBottom)

    ViewCompat.setOnApplyWindowInsetsListener(this) { view, insets ->
        val typeInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars())
        view.updateLayoutParams {
            (this as? ViewGroup.MarginLayoutParams)?.let {
                updateMarginsRelative(
                    start = initialLeft + (if (left) typeInsets.left else 0),
                    top = initialTop + (if (top) typeInsets.top else 0),
                    end = initialRight + (if (right) typeInsets.right else 0),
                    bottom = initialBottom + (if (bottom) typeInsets.bottom else 0)
                )
            }
        }

        insets
    }
}

fun RecyclerView.setOnItemClickListener(onItemClickListener: ((holder: RecyclerView.ViewHolder, position: Int) -> Unit)?) {
    val mAttachListener: RecyclerView.OnChildAttachStateChangeListener = object : RecyclerView.OnChildAttachStateChangeListener {
        override fun onChildViewAttachedToWindow(view: View) {
            if (onItemClickListener != null) {
                view.setOnClickListener {
                    val holder = getChildViewHolder(it)
                    onItemClickListener.invoke(holder, holder.adapterPosition)
                }
            }
        }
        override fun onChildViewDetachedFromWindow(view: View) {}
    }

    addOnChildAttachStateChangeListener(mAttachListener)
//    removeOnChildAttachStateChangeListener(mAttachListener)
}

fun RecyclerView.setOnItemLongClickListener(onItemLongClickListener: ((holder: RecyclerView.ViewHolder, position: Int) -> Boolean)?) {
    val mAttachListener: RecyclerView.OnChildAttachStateChangeListener = object : RecyclerView.OnChildAttachStateChangeListener {
        override fun onChildViewAttachedToWindow(view: View) {
            if (onItemLongClickListener != null) {
                view.setOnLongClickListener {
                    val holder = getChildViewHolder(it)
                    onItemLongClickListener.invoke(holder, holder.adapterPosition)
                }
            }
        }
        override fun onChildViewDetachedFromWindow(view: View) {}
    }

    addOnChildAttachStateChangeListener(mAttachListener)
//    removeOnChildAttachStateChangeListener(mAttachListener)
}


/**
 * Reduces drag sensitivity of [ViewPager2] widget
 * https://gist.github.com/AlShevelev/ea43096e8f66b0ec45a0ec0dd1e8cacc#file-gistfile1-txt
 */
fun ViewPager2.reduceDragSensitivity() {
    val recyclerViewField = ViewPager2::class.java.getDeclaredField("mRecyclerView")
    recyclerViewField.isAccessible = true
    val recyclerView = recyclerViewField.get(this) as RecyclerView

    val touchSlopField = RecyclerView::class.java.getDeclaredField("mTouchSlop")
    touchSlopField.isAccessible = true
    val touchSlop = touchSlopField.get(recyclerView) as Int
    touchSlopField.set(recyclerView, touchSlop * 4)       // "8" was obtained experimentally
}