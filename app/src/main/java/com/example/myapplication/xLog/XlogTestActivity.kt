package com.example.myapplication.xLog

import android.app.Activity
import android.os.Bundle
import android.os.Environment
import com.example.myapplication.BuildConfig
import com.example.myapplication.R
import com.tencent.mars.xlog.Log
import com.tencent.mars.xlog.Xlog
import com.tencent.mars.xlog.Xlog.XLogConfig


/**
 * @Author qipeng
 * @Date 2024/7/15
 * @Desc
 */
class XlogTestActivity :Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_xlog_test)
        initXlog()
    }

    private fun initXlog() {
        val SDCARD = Environment.getExternalStorageDirectory().absolutePath
        val logPath = "$SDCARD/marssample/log"
        val cachePath = "${this.filesDir}/xlog"
        val logFileName = "XlogFile"

        System.loadLibrary("c++_shared")
        System.loadLibrary("marsxlog")

        //init xlog
        val logConfig = XLogConfig()
        logConfig.mode = Xlog.AppednerModeAsync
        logConfig.logdir = logPath
        logConfig.nameprefix = logFileName
        logConfig.pubkey = ""
        logConfig.compressmode = Xlog.ZLIB_MODE
        logConfig.compresslevel = 0
        logConfig.cachedir = ""
        logConfig.cachedays = 0
        if (BuildConfig.DEBUG) {
            logConfig.level = Xlog.LEVEL_VERBOSE
        } else {
            logConfig.level = Xlog.LEVEL_INFO
        }
        Log.setLogImp(Xlog())
    }


    override fun onDestroy() {
        super.onDestroy()
        Log.appenderClose();
    }
}