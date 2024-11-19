package com.example.myapplication.application

import android.app.Application
import android.util.Log
import com.alibaba.android.arouter.launcher.ARouter
import com.example.myapplication.BuildConfig
import com.example.myapplication.webView.WebViewManager
import com.opensource.svgaplayer.SVGAParser
import com.tencent.mmkv.MMKV


/**
 * @date 2020/4/28
 * @author qipeng
 * @desc application
 */
class PeterApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {           // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog()    // 打印日志
            ARouter.openDebug()   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        Log.d("peter", "PeterApplication")
        SVGAParser.shareParser().init(this)
        ARouter.init(this)
        WebViewManager.prepare(this)
        // init MMKV
        val rootDir = MMKV.initialize(this)
        Log.d("peter", "rootDir: $rootDir")
    }

    companion object {
        fun getInstance(): PeterApplication {
            return PeterApplication()
        }
    }

    override fun onTerminate() {
        super.onTerminate()
        WebViewManager.destroy()
    }

}