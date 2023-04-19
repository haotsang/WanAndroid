package com.haotsang.wanandroid.ui.architecture

import android.view.LayoutInflater
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.google.android.flexbox.FlexboxLayout
import com.haotsang.wanandroid.R
import com.haotsang.wanandroid.model.bean.Category
import java.util.LinkedList
import java.util.Queue

class ArchitectureAdapter(
    data: MutableList<Category>? = null
) : BaseQuickAdapter<Category, BaseViewHolder>(R.layout.architecture_rv_item, data) {

    private var mInflater: LayoutInflater? = null
    private val mFlexItemTextViewCaches: Queue<TextView> = LinkedList()

    override fun convert(holder: BaseViewHolder, item: Category) {
        holder.setText(R.id.tv_title, item.name)
        val fbl: FlexboxLayout = holder.getView(R.id.fbl)

        for (cate in item.children) {
            val tvChild =  createOrGetCacheFlexItemTextView(fbl)?.apply {
                text = cate.name
                setOnClickListener {  }
            }
            fbl.addView(tvChild)
        }
    }

    override fun onViewRecycled(holder: BaseViewHolder) {
        super.onViewRecycled(holder)
        val fbl = holder.getView<FlexboxLayout>(R.id.fbl)
        for (i in 0 until fbl.childCount) {
            mFlexItemTextViewCaches.offer(fbl.getChildAt(i) as TextView)
        }
        fbl.removeAllViews()
    }


    private fun createOrGetCacheFlexItemTextView(fbl: FlexboxLayout): TextView? {
        val tv = mFlexItemTextViewCaches.poll()
        return tv ?: createFlexItemTextView(fbl)
    }

    private fun createFlexItemTextView(fbl: FlexboxLayout): TextView? {
        if (mInflater == null) {
            mInflater = LayoutInflater.from(fbl.context)
        }
        return mInflater?.inflate(R.layout.architecture_rv_child_item, fbl, false) as? TextView

    }
}