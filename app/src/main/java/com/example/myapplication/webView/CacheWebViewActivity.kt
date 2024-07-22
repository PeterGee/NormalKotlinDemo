package com.example.myapplication.webView

import android.os.Bundle
import android.view.KeyEvent
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.myapplication.R
import kotlinx.android.synthetic.main.actiivty_cache_webview.layoutWeb
import kotlinx.android.synthetic.main.actiivty_cache_webview.rootView

/**
 * @Author qipeng
 * @Date 2024/7/22
 * @Desc
 */
class CacheWebViewActivity : AppCompatActivity() {
    private val mUrl = "www.baidu.com"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.actiivty_cache_webview)
        initWebView()
    }

    private fun initWebView() {
        val mWebView = WebViewManager.obtain(this)
        val mLayoutParams = ConstraintLayout.LayoutParams(
            ConstraintLayout.LayoutParams.MATCH_CONSTRAINT,
            ConstraintLayout.LayoutParams.MATCH_CONSTRAINT
        )
        mWebView.layoutParams = mLayoutParams
      /*  mWebView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                mWebView.loadUrl(request?.url.toString())
                return true
            }
        }*/
        rootView.addView(mWebView)
        mWebView.loadUrl(mUrl)
    }

}