package com.haotsang.wanandroid.model.bean

import java.io.Serializable

data class Article(val id: Long,
                    val originId: Int?,
                    val title: String,
                    val chapterId: Int,
                    val chapterName: String?,
                    val envelopePic: String,
                    val link: String,
                    val author: String?,
                    val origin: String,
                    val publishTime: Long,
                    val zan: Int,
                    val desc: String?,
                    val visible: Int,
                    val niceDate: String,
                    val niceShareDate: String,
                    val courseId: Int,
                    var collect: Boolean,
                    val apkLink:String,
                    val projectLink:String,
                    val superChapterId:Int,
                    val superChapterName:String?,
                    val type:Int,
                    val fresh:Boolean,
                    val audit:Int,
                    val prefix:String,
                    val selfVisible:Int,
                    val shareDate:Long?,
                    val shareUser:String?,
                    val tags: MutableList<Tag>?,
                    val userId:Int
): Serializable {
    var top = false
}