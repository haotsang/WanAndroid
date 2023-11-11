package com.haotsang.wanandroid.ui

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.TextUtils
import android.text.style.ForegroundColorSpan
import android.text.style.UnderlineSpan
import com.haotsang.wanandroid.base.BaseActivity
import com.haotsang.wanandroid.databinding.CrashActivityBinding
import java.io.PrintWriter
import java.io.StringWriter
import java.util.regex.Matcher
import java.util.regex.Pattern

class CrashActivity : BaseActivity<CrashActivityBinding>(CrashActivityBinding::inflate) {

    companion object {

        private const val INTENT_KEY_IN_THROWABLE: String = "throwable"

        /** 系统包前缀列表 */
        private val SYSTEM_PACKAGE_PREFIX_LIST: Array<String> = arrayOf("android", "com.android",
            "androidx", "com.google.android", "java", "javax", "dalvik", "kotlin")

        /** 报错代码行数正则表达式 */
        private val CODE_REGEX: Pattern = Pattern.compile("\\(\\w+\\.\\w+:\\d+\\)")

        fun start(context: Context, throwable: Throwable?) {
            if (throwable == null) {
                return
            }
            val intent = Intent(context, CrashActivity::class.java)
            intent.putExtra(INTENT_KEY_IN_THROWABLE, throwable)
            intent.addFlags(
                Intent.FLAG_ACTIVITY_NEW_TASK or
                        Intent.FLAG_ACTIVITY_CLEAR_TOP or
                        Intent.FLAG_ACTIVITY_CLEAR_TASK
            )
            context.startActivity(intent)
        }
    }


    private var stackTrace: String? = null

    override fun initialize() {
        super.initialize()

        val throwable: Throwable = (intent.getSerializableExtra(INTENT_KEY_IN_THROWABLE) as? Throwable) ?: return
//        titleView?.text = throwable.javaClass.simpleName
        val stringWriter = StringWriter()
        val printWriter = PrintWriter(stringWriter)
        throwable.printStackTrace(printWriter)
        throwable.cause?.printStackTrace(printWriter)
        stackTrace = stringWriter.toString()
        val matcher: Matcher = CODE_REGEX.matcher(stackTrace!!)
        val spannable = SpannableStringBuilder(stackTrace)
        if (spannable.isNotEmpty()) {
            while (matcher.find()) {
                // 不包含左括号（
                val start: Int = matcher.start() + "(".length
                // 不包含右括号 ）
                val end: Int = matcher.end() - ")".length

                // 代码信息颜色
                var codeColor: Int = Color.parseColor("#999999")
                val lineIndex: Int = stackTrace!!.lastIndexOf("at ", start)
                if (lineIndex != -1) {
                    val lineData: String = spannable.subSequence(lineIndex, start).toString()
                    if (TextUtils.isEmpty(lineData)) {
                        continue
                    }
                    // 是否高亮代码行数
                    var highlight = true
                    for (packagePrefix: String? in SYSTEM_PACKAGE_PREFIX_LIST) {
                        if (lineData.startsWith("at $packagePrefix")) {
                            highlight = false
                            break
                        }
                    }
                    if (highlight) {
                        codeColor = Color.parseColor("#287BDE")
                    }
                }

                // 设置前景
                spannable.setSpan(ForegroundColorSpan(codeColor), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                // 设置下划线
                spannable.setSpan(UnderlineSpan(), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            }

            mBinding.tvCrashMessage.text = spannable
        }
    }

}