package com.haotsang.wanandroid.ui.points.rank

import android.animation.Animator
import android.animation.ValueAnimator
import android.view.animation.DecelerateInterpolator
import android.widget.ProgressBar
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.haotsang.wanandroid.R
import com.haotsang.wanandroid.common.loadmore.BaseLoadMoreAdapter
import com.haotsang.wanandroid.model.bean.PointRank

class PointsRankAdapter :
    BaseLoadMoreAdapter<PointRank, BaseViewHolder>(R.layout.points_rank_item) {

    private val mScale = 1000
    private var mMax = 0
    override fun setNewInstance(list: MutableList<PointRank>?) {
        super.setNewInstance(list)
        list?.firstOrNull()?.coinCount?.let { mMax = it }
    }

    override fun convert(holder: BaseViewHolder, item: PointRank) {
        val progressBar = holder.getView<ProgressBar>(R.id.pb_progress)
        progressBar.max = mMax * mScale

        cancelProgressAnim(progressBar)
        if (!item.animated) {
            item.animated = true
            doProgressAnim(progressBar, item.coinCount)
        } else {
            progressBar.progress = item.coinCount * mScale
        }


        holder.setText(R.id.tvNo, "${holder.absoluteAdapterPosition + 1}")
        holder.setText(R.id.tvName, item.username)
        holder.setText(R.id.tvPoints, item.coinCount.toString())
    }


    private fun doProgressAnim(pb: ProgressBar, to: Int) {
        val animator = ValueAnimator.ofInt(0, to)
        animator.duration = 1000
        animator.interpolator = DecelerateInterpolator()
        animator.addUpdateListener { animation ->
            pb.progress = animation.animatedValue as Int * mScale
        }
        pb.tag = animator
        animator.start()
    }

    private fun cancelProgressAnim(pb: ProgressBar) {
        val obj = pb.tag
        if (obj is Animator) {
            obj.cancel()
        }
        pb.tag = null
    }
}