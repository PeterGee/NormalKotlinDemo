package com.example.myapplication.okHttp

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.myapplication.R
import com.example.myapplication.okHttp.interceptor.LogInterceptor
import com.example.myapplication.okHttp.util.LogUtil
import kotlinx.android.synthetic.main.activity_okhttp_test.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import okio.BufferedSink
import java.io.IOException
import kotlin.math.log

/**
 * @date 2021/6/30
 * @author qipeng
 * @desc okHttp请求
 */
@Suppress("BlockingMethodInNonBlockingContext")
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
            doSyncGet()
        }

        btnGetTwo.setOnClickListener {
            doAsyncGet()
        }

        btnGetHeader.setOnClickListener {
            accessHeader()
        }

        btnPostString.setOnClickListener {
            postString()
        }

        btnPost.setOnClickListener {
            doPostRequest()
        }

        btnPostStream.setOnClickListener {
            postStream()
        }

    }

    private fun postStream() {
            val requestBody = object : RequestBody() {
                override fun contentType() = MEDIA_TYPE_MARKDOWN

                /** Writes the content of this request to [sink]. */
                override fun writeTo(sink: BufferedSink) {
                    sink.writeUtf8("Numbers\n")
                    sink.writeUtf8("-------\n")
                    for (i in 2..10) {
                        sink.writeUtf8(String.format(" * $i = ${factor(i)}\n"))
                    }
                }

                // 因式分解
                private fun factor(n: Int): String {
                    for (i in 2 until n) {
                        val x = n / i
                        if (x * i == n) return "${factor(x)} × $i"
                    }
                    return n.toString()
                }
            }

            val request = Request.Builder()
                .url("https://api.github.com/markdown/raw")
                .post(requestBody)
                .build()

           GlobalScope.launch {
               mClient.newCall(request).execute().use { response ->
                   if (!response.isSuccessful) throw IOException("Unexpected code $response")

                   LogUtil.D(log=response.body!!.string())
           }

        }

    }

    private fun initRequest() {
        mRequest = Request.Builder().url(mUrl2).build()
        mRequest2 = Request.Builder().url(mUrl2).build()
    }

    private fun doSyncGet() {
        mRequest?.let {
            val job = GlobalScope.launch {
                mClient.newCall(it).execute().use { response ->

                    if (response.isSuccessful) {
                        for ((name, value) in response.headers) {
                            LogUtil.D(log = "name ===$name   value===$value")
                        }
                        LogUtil.D(log = "response body== ${response.body.toString()}")
                    } else {
                        LogUtil.D(log = "request error=== ${response.body.toString()}")
                    }

                }

            }
            job.isCompleted
        }
    }

    private fun doAsyncGet() {
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


    private fun accessHeader() {
        val mRequest = Request.Builder()
            .url("https://api.github.com/repos/square/okhttp/issues")
            .header("User-Agent", "OkHttp Headers.java")
            .addHeader("Accept", "application/json; q=0.5")
            .addHeader("Accept", "application/vnd.github.v3+json")
            .build()

        GlobalScope.launch {
            mClient.newCall(mRequest).execute().use { response ->
                if (!response.isSuccessful) LogUtil.D(log = "response error ==== ${response.code}")

                LogUtil.D(log = "Server: ${response.header("Server")}")
                LogUtil.D(log = "Date: ${response.header("Date")}")
                LogUtil.D(log = "Vary: ${response.headers("Vary")}")
                LogUtil.D(log = "哈哈哈: ${response.header("哈哈哈")}")
            }
        }

    }

    private fun postString() {
        val postBody = """
        |Releases
        |--------
        |
        | * _1.0_ May 6, 2013
        | * _1.1_ June 15, 2013
        | * _1.2_ August 11, 2013
        |""".trimMargin()

        val request = Request.Builder()
            .url("https://api.github.com/markdown/raw")
            .post(postBody.toRequestBody(MEDIA_TYPE_MARKDOWN))
            .build()

        GlobalScope.launch {
            mClient.newCall(request).execute().use { response ->
                if (!response.isSuccessful) throw IOException("Unexpected code $response")

                LogUtil.D(log = response.body!!.string())
            }
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
        // 不设置拦截器
        return OkHttpClient.Builder().build()

        // 应用拦截器
        // return OkHttpClient.Builder().addInterceptor(LogInterceptor()).build()

        // 网络拦截器
        // return OkHttpClient.Builder().addNetworkInterceptor(LogInterceptor()).build()

        // 压缩拦截器
        // return OkHttpClient.Builder().addNetworkInterceptor(GzipRequestInterceptor()).build()

        // 缓存拦截器
        // return OkHttpClient.Builder().addNetworkInterceptor(CacheAgeInterceptor()).build()
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

    companion object {
        val MEDIA_TYPE_MARKDOWN = "text/x-markdown; charset=utf-8".toMediaType()
    }
}