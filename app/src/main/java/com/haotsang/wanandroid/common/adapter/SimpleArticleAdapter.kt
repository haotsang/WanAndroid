package com.haotsang.wanandroid.common.adapter

import android.annotation.SuppressLint
import android.widget.ImageView
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.haotsang.wanandroid.R
import com.haotsang.wanandroid.common.loadmore.BaseLoadMoreAdapter
import com.haotsang.wanandroid.model.bean.Article
import com.haotsang.wanandroid.utils.ext.htmlToSpanned

class SimpleArticleAdapter(layoutResId: Int = R.layout.article_simple_item) :
    BaseLoadMoreAdapter<Article, BaseViewHolder>(layoutResId) {

    init {
        addChildClickViewIds(R.id.iv_collect)
    }

    @SuppressLint("SetTextI18n")
    override fun convert(holder: BaseViewHolder, item: Article) {
        holder.setText(R.id.tv_author, when {
            !item.author.isNullOrEmpty() -> {
                item.author
            }
            !item.shareUser.isNullOrEmpty() -> {
                item.shareUser
            }
            else -> context.getString(R.string.anonymous)
        })
        holder.setGone(R.id.tv_fresh, !item.fresh)
        holder.setText(R.id.tv_title, item.title.htmlToSpanned())
        holder.setText(R.id.tv_time, item.niceDate)

        val collect = holder.getView<ImageView>(R.id.iv_collect)
        collect.isSelected = item.collect

    }
}