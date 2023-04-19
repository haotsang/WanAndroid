package com.haotsang.wanandroid.common.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.haotsang.wanandroid.R
import com.haotsang.wanandroid.model.bean.Banner
import com.youth.banner.adapter.BannerAdapter

class BannerImageAdapter2(imageUrls: MutableList<Banner>, private val context: Context) :
    BannerAdapter<Banner, BannerImageAdapter2.ImageHolder>(imageUrls) {

    override fun onCreateHolder(parent: ViewGroup?, viewType: Int): ImageHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.banner_item, parent, false)
        return ImageHolder(view)
    }

    override fun onBindView(holder: ImageHolder, data: Banner, position: Int, size: Int) {
        Glide.with(holder.itemView)
            .load(data.imagePath)
            .placeholder(R.mipmap.ic_launcher)
            .error(R.mipmap.ic_launcher)
            .apply(RequestOptions.bitmapTransform(RoundedCorners(30)))
            .into(holder.imageView)
    }

    class ImageHolder(view: View) : RecyclerView.ViewHolder(view) {
        //        var imageView: ImageView = view as ImageView
        var imageView = view.findViewById(R.id.img_banner) as ImageView
        var tvTitle = view.findViewById(R.id.tv_title) as TextView
    }

}