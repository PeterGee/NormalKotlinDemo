package com.example.myapplication.okHttp

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.myapplication.R
import com.example.myapplication.okHttp.interceptor.CacheAgeInterceptor
import com.example.myapplication.okHttp.interceptor.GzipRequestInterceptor
import com.example.myapplication.okHttp.interceptor.LogInterceptor
import kotlinx.android.synthetic.main.activity_okhttp_test.*
import okhttp3.*
import java.io.IOException

/**
 * @date 2021/6/30
 * @author qipeng
 * @desc okHttp请求
 */
class OkHttpTestActivity : AppCompatActivity() {

    private val TAG = "tag"
    private var mUrl = "https://www.publicobject.com/helloworld.txt"
    private var mUrl2 = "https://www.baidu.com/"
    private var mUrl3 = "https://www.nytimes.com/"

    // okHttpClient
    private val mClient = getOkClient()
    private var mRequest: Request? = null
    private var mRequest2: Request? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_okhttp_test)
        requestPermission()
        intiView()
    }

    private fun intiView() {
        initRequest()
        btnGet.setOnClickListener {
            doGetRequest()
        }
        btnPost.setOnClickListener {
            doPostRequest()
        }
        btnGetTwo.setOnClickListener {
            doGetRequestTwo()
        }
    }

    private fun initRequest() {
        mRequest = Request.Builder().url(mUrl).build()
        mRequest2 = Request.Builder().url(mUrl2).build()
        // mRequest2 = Request.Builder().url(mUrl3).build()
    }

    private fun doGetRequest() {
        mRequest?.let {
           mClient.newCall(it).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.d(TAG, "fail e===${e.message}   cause=== ${e.cause}")
                }

                override fun onResponse(call: Call, response: Response) {
                    Log.d(TAG, " get success  response=== ${response.body.toString()}")
                    updateText(response)
                    response.body?.close()
                }

            })
        }

    }

    private fun doGetRequestTwo() {
        mRequest2?.let {
            mClient.newCall(it).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.d(TAG, "fail e===${e.message}   cause=== ${e.cause}")
                }

                override fun onResponse(call: Call, response: Response) {
                    Log.d(TAG, " get success  response=== ${response.body.toString()}")
                    updateText(response)
                }

            })
        }
    }

    private fun doPostRequest() {
        mRequest?.let {
            mClient.newCall(it).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.d(TAG, " post fail")
                }

                override fun onResponse(call: Call, response: Response) {
                    Log.d(TAG, " post success  response=== ${response.body.toString()}")
                    updateText(response)

                }

            })
        }

    }

    private fun updateText(response: Response) {
        runOnUiThread {
            tvResponseContent.text = response.toString()
        }
    }

    private fun getOkClient(): OkHttpClient {
        // 应用拦截器
        // return OkHttpClient.Builder().addInterceptor(LogInterceptor()).build()

        // 网络拦截器
        // return OkHttpClient.Builder().addNetworkInterceptor(LogInterceptor()).build()

        // 压缩拦截器
       // return OkHttpClient.Builder().addNetworkInterceptor(GzipRequestInterceptor()).build()

       // 缓存拦截器
        return OkHttpClient.Builder().addNetworkInterceptor(CacheAgeInterceptor()).build()
    }

    private fun eventListener() {
        // okEvent
        // return OkHttpClient.Builder().eventListener(OkEventListener()).build()

        // okEventFactory
        // return OkHttpClient.Builder().eventListenerFactory(OkEventFactoryListener.create()).build()
    }

    private fun requestPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.INTERNET
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.INTERNET), 0)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            0 -> {
                Log.d(TAG, "请求权限成功")
            }
        }
    }
}