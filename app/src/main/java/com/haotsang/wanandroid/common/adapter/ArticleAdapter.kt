package com.haotsang.wanandroid.common.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.haotsang.wanandroid.R
import com.haotsang.wanandroid.common.loadmore.BaseLoadMoreAdapter
import com.haotsang.wanandroid.model.bean.Article
import com.haotsang.wanandroid.utils.ext.htmlToSpanned

class ArticleAdapter(layoutResId: Int = R.layout.article_item) :
    BaseLoadMoreAdapter<Article, BaseViewHolder>(layoutResId) {

    init {
        addChildClickViewIds(R.id.iv_collect)
    }

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

        holder.setGone(R.id.tv_top, !item.top)
        holder.setGone(R.id.tv_fresh, !(item.fresh && !item.top))

        val tag = holder.getView<TextView>(R.id.tv_tag)
        tag.visibility = if (item.tags.isNullOrEmpty()) {
            View.GONE
        } else {
            tag.text = item.tags[0].name
            View.VISIBLE
        }

        holder.setText(R.id.tv_chapter, when {
            !item.superChapterName.isNullOrEmpty() && !item.chapterName.isNullOrEmpty() ->
                "${item.superChapterName.htmlToSpanned()}/${item.chapterName.htmlToSpanned()}"
            item.superChapterName.isNullOrEmpty() && !item.chapterName.isNullOrEmpty() ->
                item.chapterName.htmlToSpanned()
            !item.superChapterName.isNullOrEmpty() && item.chapterName.isNullOrEmpty() ->
                item.superChapterName.htmlToSpanned()
            else -> ""
        })

        holder.setText(R.id.tv_title, item.title.htmlToSpanned())
        holder.setText(R.id.tv_desc, item.desc.htmlToSpanned())
        holder.setGone(R.id.tv_desc, item.desc.isNullOrEmpty())
        holder.setText(R.id.tv_time, item.niceDate)

        val collect = holder.getView<ImageView>(R.id.iv_collect)
        collect.isSelected = item.collect
    }

}