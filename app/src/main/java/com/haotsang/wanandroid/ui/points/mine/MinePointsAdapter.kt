package com.haotsang.wanandroid.ui.points.mine

import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.haotsang.wanandroid.R
import com.haotsang.wanandroid.common.loadmore.BaseLoadMoreAdapter
import com.haotsang.wanandroid.model.bean.PointRecord
import java.text.SimpleDateFormat
import java.util.Locale

class MinePointsAdapter :
    BaseLoadMoreAdapter<PointRecord, BaseViewHolder>(R.layout.mine_points_item) {
    override fun convert(holder: BaseViewHolder, item: PointRecord) {
        holder.setText(R.id.tvReason, item.reason)
        holder.setText(R.id.tvTime,
            SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(item.date)
        )
        holder.setText(R.id.tvPoint, "+${item.coinCount}")
    }

}