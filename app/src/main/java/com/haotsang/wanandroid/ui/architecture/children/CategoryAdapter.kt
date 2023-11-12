package com.haotsang.wanandroid.ui.architecture.children

import android.annotation.SuppressLint
import android.widget.CheckedTextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.haotsang.wanandroid.R
import com.haotsang.wanandroid.model.bean.Category
import com.haotsang.wanandroid.utils.ext.htmlToSpanned

class CategoryAdapter(layoutResId: Int = R.layout.category_sub_item) :
    BaseQuickAdapter<Category, BaseViewHolder>(layoutResId) {

    private var checkedPosition = 0
    var onCheckedListener: ((position: Int) -> Unit)? = null

    override fun convert(holder: BaseViewHolder, item: Category) {
        holder.itemView.run {
            val ctvCategory = holder.getView<CheckedTextView>(R.id.ctvCategory)
            ctvCategory.text = item.name.htmlToSpanned()
            ctvCategory.isChecked = checkedPosition == holder.absoluteAdapterPosition
            setOnClickListener {
                val position = holder.absoluteAdapterPosition
                check(position)
                onCheckedListener?.invoke(position)
            }
//            updateLayoutParams<ViewGroup.MarginLayoutParams> {
//                marginStart =
//                    if (holder.absoluteAdapterPosition == 0) 8.dpToPx().toInt() else 0.dpToPx().toInt()
//            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun check(position: Int) {
        checkedPosition = position
        notifyDataSetChanged()
    }

}