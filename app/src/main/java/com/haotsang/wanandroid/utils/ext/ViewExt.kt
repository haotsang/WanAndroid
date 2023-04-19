package com.haotsang.wanandroid.utils.ext

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2

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