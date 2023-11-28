package com.example.myapplication.jetpack_demo.lifeCycle

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.okHttp.util.LogUtil

/**
 * @Author qipeng
 * @Date 2023/11/28
 * @Desc
 */
class LifeCycleTestActivity : AppCompatActivity() {
    private val TAG = this.javaClass.canonicalName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LogUtil.D(TAG, "onCreate()")
        finish()
    }

    override fun onStart() {
        super.onStart()
        LogUtil.D(TAG, "onStart()")
    }

    override fun onResume() {
        super.onResume()
        LogUtil.D(TAG, "onResume()")
    }

    override fun onPause() {
        super.onPause()
        LogUtil.D(TAG, "onPause()")
    }

    override fun onStop() {
        super.onStop()
        LogUtil.D(TAG, "onStop()")
    }

    override fun onDestroy() {
        super.onDestroy()
        LogUtil.D(TAG, "onDestroy()")
    }
}