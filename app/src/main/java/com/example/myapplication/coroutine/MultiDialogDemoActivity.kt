package com.example.myapplication.coroutine

import android.app.Activity
import android.net.http.HttpResponseCache
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityMulityDialogBinding
import com.opensource.svgaplayer.SVGAParser
import com.opensource.svgaplayer.SVGAVideoEntity
import java.io.File
import java.net.URL


/**
 * @Author qipeng
 * @Date 2024/6/1
 * @Desc multy
 */
class MultiDialogDemoActivity : Activity() {
    private var mBinding: ActivityMulityDialogBinding? = null
    private var mParser:SVGAParser?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_mulity_dialog)

        initListener()

        initSvgaConfig()
    }

    private fun initSvgaConfig() {
        mParser = SVGAParser.shareParser()
        // 本地资源文件
        mParser?.decodeFromAssets("posche.svga",object :SVGAParser.ParseCompletion{
            override fun onComplete(videoItem: SVGAVideoEntity) {
                Log.d("tag","onSuccess")
               // mBinding?.svgaImage?.setVideoItem(videoItem)
                // mBinding?.svgaImage?.startAnimation()
            }

            override fun onError() {
                Log.d("tag","onError")
            }

        })

        // 网络图片
        mParser?.decodeFromURL(URL(""),object :SVGAParser.ParseCompletion{
            override fun onComplete(videoItem: SVGAVideoEntity) {
            }

            override fun onError() {
            }

        })

        // 设置缓存
        val cacheDir = File(applicationContext.cacheDir, "http")
        HttpResponseCache.install(cacheDir, 1024 * 1024 * 128)

    }

    private fun initListener() {
        mBinding?.btnShowDialog?.setOnClickListener{

        }
    }

}