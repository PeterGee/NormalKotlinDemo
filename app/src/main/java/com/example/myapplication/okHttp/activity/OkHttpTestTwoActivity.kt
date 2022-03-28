package com.example.myapplication.okHttp.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import com.example.myapplication.okHttp.util.LogUtil
import kotlinx.android.synthetic.main.activity_okhttp_test_two.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request

/*
 * @date 2021/7/22 
 * @author qipeng
 * @desc
 */
class OkHttpTestTwoActivity : AppCompatActivity(){

    private val mUrl = "https://www.baidu.com"
    private val mClient = OkHttpClient.Builder().build()

    private val mRequest = Request.Builder().url(mUrl).build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_okhttp_test_two)
        initView()
    }

    private fun initView() {
        btnSyncGet.setOnClickListener {
            doSyncGetRequest()
        }
    }

    private fun doSyncGetRequest() {
        GlobalScope.launch {
            mClient.newCall(mRequest).execute().use { response ->
                if (response.isSuccessful) {
                    LogUtil.D(log = "request success code is ${response.code}  body is ${response.body.toString()}")
                } else {
                    LogUtil.D(log="request error code is ${response.code}")
                }
            }

        }


    }

}