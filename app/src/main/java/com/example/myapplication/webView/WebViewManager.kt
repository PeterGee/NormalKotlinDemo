package com.example.myapplication.webView

import android.content.Context
import android.content.MutableContextWrapper
import android.os.Looper
import android.view.ViewGroup
import android.webkit.WebView

/**
 * @Author qipeng
 * @Date 2024/7/22
 * @Desc
 */
object WebViewManager{



        // 为了阅读体验，省略部分代码

  private val webViewCache: MutableList<WebView> = ArrayList(1)

    private fun create(context: Context): WebView {
        return WebView(context)
    }

        fun prepare(context: Context) {
            if (webViewCache.isEmpty()) {
                Looper.myQueue().addIdleHandler {
                    webViewCache.add(create(MutableContextWrapper(context)))
                    false
                }
            }
        }

        fun obtain(context: Context): WebView {
            if (webViewCache.isEmpty()) {
                webViewCache.add(create(MutableContextWrapper(context)))
            }
            val webView = webViewCache.removeFirst()
            val contextWrapper = webView.context as MutableContextWrapper
            contextWrapper.baseContext = context
            webView.clearHistory()
            webView.resumeTimers()
            return webView
        }

        fun recycle(webView: WebView) {
            try {
                webView.stopLoading()
                webView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null)
                webView.clearHistory()
                webView.pauseTimers()
                webView.webChromeClient = null
                val parent = webView.parent
                if (parent != null) {
                    (parent as ViewGroup).removeView(webView)
                }
                val contextWrapper = webView.context as MutableContextWrapper
                contextWrapper.baseContext = webView.context.applicationContext
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                if (!webViewCache.contains(webView)) {
                    webViewCache.add(webView)
                }
            }
        }

        fun destroy() {
            try {
                webViewCache.forEach {
                    it.removeAllViews()
                    it.destroy()
                    webViewCache.remove(it)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }


}