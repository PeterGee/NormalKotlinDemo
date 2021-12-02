package com.example.myapplication.okHttp

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myapplication.R
import com.example.myapplication.okHttp.adapter.GridSpanLookAdapter
import com.example.myapplication.okHttp.bean.Person
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
    private var mUrl = "https://publicobject.com/helloworld.txt"
    private var mUrl2 = "https://www.baidu.com/"

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
        spanSizeLookTest()
    }

    private fun spanSizeLookTest() {
        val dataList= arrayListOf<Person>()
        dataList.add(Person("张三",18,1))
        dataList.add(Person("李四",18,2))
        dataList.add(Person("王五",18,3))
        dataList.add(Person("王五",18,3))
        dataList.add(Person("王五",18,3))
        dataList.add(Person("王五",18,3))
        dataList.add(Person("王五",18,3))

       val manager=GridLayoutManager(this,4)
        manager.spanSizeLookup=object :GridLayoutManager.SpanSizeLookup(){
            override fun getSpanSize(position: Int): Int {
                return dataList[position].type
            }

        }
        rv_manager_test.layoutManager=manager
        rv_manager_test.adapter=GridSpanLookAdapter(dataList)
    }

    private fun initRequest() {
        mRequest = Request.Builder().url(mUrl).build()
        mRequest2 = Request.Builder().url(mUrl2).build()
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
        // okEvent
        return OkHttpClient.Builder().eventListener(OkEventListener()).build()

        // okEventFactory

       // return OkHttpClient.Builder().eventListenerFactory(OkEventFactoryListener.create()).build()
    }

    private fun requestPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED
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