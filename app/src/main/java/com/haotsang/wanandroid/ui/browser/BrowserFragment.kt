package com.haotsang.wanandroid.ui.browser

import android.content.Context
import android.os.Bundle
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import com.haotsang.wanandroid.R
import com.haotsang.wanandroid.base.BaseFragment
import com.haotsang.wanandroid.databinding.BrowserFragmentBinding
import com.haotsang.wanandroid.ui.main.MainActivity
import com.haotsang.wanandroid.utils.ext.activity

class BrowserFragment : BaseFragment<BrowserFragmentBinding>(BrowserFragmentBinding::inflate) {

    private var webView: WebView? = null

    override fun initialize() {
        super.initialize()
        mBinding?.toolbar?.apply {
            setNavigationIcon(R.drawable.baseline_arrow_back_24)
            setNavigationOnClickListener {
                requireActivity().supportFragmentManager.popBackStack()
            }

            title = requireArguments().getString(TITLE, "")
        }

        webView = WebView(requireContext()).apply {
            settings.javaScriptEnabled = true
            loadUrl(requireArguments().getString(URL) ?: "about:blank")

            webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(
                    view: WebView?,
                    request: WebResourceRequest?
                ): Boolean {
                    return false
                }
            }
        }
        mBinding?.contentFrame?.addView(
            webView,
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.MATCH_PARENT
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        webView?.destroy()
        webView = null
    }

    companion object {
        private const val ID = "_id"
        private const val TITLE = "_title"
        private const val URL = "_url"

        fun openUrl(fragment: Fragment?, triple: Triple<Long, String?, String?>) {
            (fragment?.activity as? MainActivity)?.switchFragmentPage(fragment, newInstance(triple))
        }

        private fun newInstance(triple: Triple<Long, String?, String?>): BrowserFragment {
            return BrowserFragment().apply {
                val bundle = Bundle()
                bundle.putLong(ID, triple.first)
                bundle.putString(TITLE, triple.second)
                bundle.putString(URL, triple.third)
                arguments = bundle
            }
        }
    }
}

