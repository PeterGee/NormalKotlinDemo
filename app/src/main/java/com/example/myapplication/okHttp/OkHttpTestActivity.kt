package com.example.myapplication.okHttp

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.lib_base.constant.connectiontime.NetWorkUtils
import com.example.myapplication.R
import com.example.myapplication.okHttp.interceptor.CacheAgeInterceptor
import com.example.myapplication.okHttp.interceptor.LogInterceptor
import com.example.myapplication.okHttp.util.LogUtil
import kotlinx.android.synthetic.main.activity_okhttp_test.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.internal.cache.CacheInterceptor
import okio.BufferedSink
import java.io.File
import java.io.IOException
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

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

        btnPostFile.setOnClickListener {
            postFile()
        }

        btnPostFormData.setOnClickListener {
            postFormData()
        }

        btnPostMultipartForm.setOnClickListener {
            postMultipartForm()
        }

        btnResponseCache.setOnClickListener {
            responseCache()
        }

        btnCancelCall.setOnClickListener {
            cancelCallMethod()
        }

        btnTimeout.setOnClickListener {
            timeoutFunction()
        }

        btnPerCall.setOnClickListener {
            perCallConfiguration()
        }

        btnHandleAuth.setOnClickListener {
            handleAuthentication()
        }
    }

    private fun handleAuthentication() {
        val client = OkHttpClient.Builder()
            .authenticator(object : Authenticator {
                @Throws(IOException::class)
                override fun authenticate(route: Route?, response: Response): Request? {
                    if (response.request.header("Authorization") != null) {
                        return null
                    }

                    LogUtil.D(log = "Authenticating for response: $response")
                    LogUtil.D(log = "Challenges: ${response.challenges()}")
                    // 设置认证凭据
                    val credential = Credentials.basic("jesse", "password1")
                    return response.request.newBuilder()
                        .header("Authorization", credential)
                        .build()
                }
            })
            .build()

        val request = Request.Builder()
            .url("https://publicobject.com/secrets/hellosecret.txt")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                LogUtil.D(log = "Response error: ${e.message}")
            }

            override fun onResponse(call: Call, response: Response) {
                LogUtil.D(log = "Response completed: ${response.body.toString()}")
            }

        })
    }

    private fun perCallConfiguration() {
        val request = Request.Builder()
            .url("https://httpbin.org/delay/1") // This URL is served with a 1 second delay.
            .build()

        val requestTwo = Request.Builder()
            .url("https://www.baidu.com") // This URL is served with a 1 second delay.
            .build()

        // set timeout 500
        val clientOne = mClient.newBuilder()
            .readTimeout(500, TimeUnit.MILLISECONDS)
            .build()
        GlobalScope.launch {
            clientOne.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    LogUtil.D(log = "Response error: ${e.message}")
                }

                override fun onResponse(call: Call, response: Response) {
                    LogUtil.D(log = "Response completed: ${response.body.toString()}")
                }

            })
        }

        // set timeout 3000
        val clientTwo = mClient.newBuilder()
            .readTimeout(3000, TimeUnit.MILLISECONDS)
            .build()

        GlobalScope.launch {
            clientTwo.newCall(requestTwo).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    LogUtil.D(log = "Response error: ${e.message}")
                }

                override fun onResponse(call: Call, response: Response) {
                    LogUtil.D(log = "Response completed: ${response.body.toString()}")

                }

            })
        }
    }


    private fun timeoutFunction() {
        val client: OkHttpClient = OkHttpClient.Builder()
            .addNetworkInterceptor(LogInterceptor())
            .connectTimeout(5, TimeUnit.SECONDS)  // 连接超时时间
            .writeTimeout(5, TimeUnit.SECONDS)      // 写超时时间
            .readTimeout(5, TimeUnit.SECONDS)       // 读超时时间
            .callTimeout(10, TimeUnit.SECONDS)      // 全过程超时时间
            .build()

        val request = Request.Builder()
            .url(mUrl2)
            .build()

        GlobalScope.launch {
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    LogUtil.D(log = "Response error: ${e.message}")
                }

                override fun onResponse(call: Call, response: Response) {
                    LogUtil.D(log = "Response completed: ${response.body.toString()}")
                }

            })
        }
    }

    private fun cancelCallMethod() {
        // init
        val executor = Executors.newScheduledThreadPool(1)
        val request = Request.Builder()
            .url("https://www.baidu.com")
            .build()
        val startTime = System.currentTimeMillis()
        val mCall = mClient.newCall(request)

        // 延迟5s
        executor.schedule({
            LogUtil.D(log = "startCallTime=== ${System.currentTimeMillis() - startTime}")
            mCall.cancel()
        }, 5, TimeUnit.SECONDS)

        // 请求
        GlobalScope.launch {
            mCall.execute().use { response ->
                if (response.isSuccessful) {
                    LogUtil.D(log = "call success response=== ${response.body.toString()}")
                } else {
                    LogUtil.D(log = "call fail code is === ${response.code}")
                }
            }
        }

    }

    private fun responseCache() {
        val directory = File(Environment.getExternalStorageDirectory(), "customCache")
        val cacheClient = OkHttpClient().newBuilder().addNetworkInterceptor(CacheAgeInterceptor())
            .cache(Cache(directory = directory, maxSize = 1 * 1024 * 1024L))
            .build()

        // CacheControl.FORCE_CACHE 强制使用缓存
        // CacheControl.FORCE_NETWORK 强制使用网络

        val cc = CacheControl.Builder()
            // 不使用缓存,但是会存储缓存数据
            // .noCache()

            // 不使用缓存，同时不存储缓存
            // .noStore()

            // 本地缓存时会使用缓存
            // .onlyIfCached()
            .minFresh(10, TimeUnit.SECONDS) // 10s刷新缓存
            .maxAge(1, TimeUnit.HOURS) // 1h最大有效时间
            .maxStale(5, TimeUnit.SECONDS) // 可以接受超时5s的响应
            .build()

        val request = Request.Builder().url(mUrl).cacheControl(CacheControl.FORCE_CACHE).build()

        GlobalScope.launch {
            // 响应1
            val responseOne = cacheClient.newCall(request).execute().use { response ->
                if (response.isSuccessful) {
                    LogUtil.D(
                        log = "request success   cacheResponse=== ${response.cacheResponse?.body?.contentType()}  " +
                                "networkResponse=== ${response.networkResponse}"
                    )
                } else {
                    LogUtil.D(log = "requestOne error   errorCode ===${response.code}")
                }
            }

            // 响应2
            val responseTwo = cacheClient.newCall(request).execute().use { response ->
                if (response.isSuccessful) {
                    LogUtil.D(
                        log = "request successTwo   cacheResponse=== ${response.cacheResponse?.body.toString()} " +
                                " networkResponse=== ${response.networkResponse}"
                    )
                } else {
                    LogUtil.D(log = "requestTwo  error   errorCode ===${response.code}")
                }
            }

            LogUtil.D(log = " responseOne===responseTwo====${responseOne == responseTwo}")

        }

    }

    private fun postMultipartForm() {
        // 将手机上文件通过formData 传递
        val requestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("title", "registry_list")
            .addFormDataPart(
                "text", "registry_list.txt",
                getFile("360/authshare", "registry_list.txt").asRequestBody(MEDIA_TYPE_MARKDOWN)
            )
            .build()

        val request = Request.Builder()
            .header("Authorization", "Client-ID $IMGUR_CLIENT_ID")
            .url("https://www.baidu.com")
            .post(requestBody)
            .build()

        mClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                LogUtil.D(log = "error  message ==== ${e.message}")
            }

            override fun onResponse(call: Call, response: Response) {
                LogUtil.D(log = "success response   code==== ${response.code}    body===== ${response.body.toString()}")
            }

        })
    }

    private fun postFormData() {
        val formData = FormBody.Builder()
            .add("search", "Jurassic Park")
            .build()

        val request = Request.Builder()
            .url("https://www.baidu.com")
            .post(formData)
            .build()

        mClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                LogUtil.D(log = "error  message ==== ${e.message}")
            }

            override fun onResponse(call: Call, response: Response) {
                LogUtil.D(log = "success response== ${response.body.toString()}")
            }

        })
    }

    private fun postFile() {
        val file = getFile("test", "testFile.txt")
        val request = Request.Builder()
            .url("https://api.github.com/markdown/raw")
            .post(file.asRequestBody(MEDIA_TYPE_MARKDOWN))
            .build()

        request.let {
            mClient.newCall(it).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    LogUtil.D(log = "response error=== ${e.cause} message=== ${e.message}")
                }

                override fun onResponse(call: Call, response: Response) {
                    LogUtil.D(log = response.body!!.string())
                }
            })
        }

    }

    private fun getFile(dir: String, fileName: String): File {
        val fileDir = File(Environment.getExternalStorageDirectory(), dir)
        if (!fileDir.exists()) {
            fileDir.mkdir()
        }
        return File(fileDir, fileName)
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

                LogUtil.D(log = response.body!!.string())
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
        // return OkHttpClient.Builder().build()

        // 应用拦截器
        // return OkHttpClient.Builder().addInterceptor(LogInterceptor()).build()
       // return OkHttpClient()
        // 网络拦截器
        return OkHttpClient.Builder().addNetworkInterceptor(LogInterceptor()).build()

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
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return
        } else {
            ActivityCompat.requestPermissions(
                this, arrayOf(
                    Manifest.permission.INTERNET,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ), 0
            )
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
        const val IMGUR_CLIENT_ID = "9199fdef135c122"
    }
    internal  class  Test{}
}