package com.example.myapplication.okHttp

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.lib_base.constant.connectiontime.NetworkListener
import com.example.myapplication.R
import com.example.myapplication.okHttp.bean.Person
import com.example.myapplication.touchablePop.SearchPop
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_okhttp_test.*
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException

/**
 * @date 2021/6/30
 * @author qipeng
 * @desc okHttp请求
 */
class OkHttpTestActivity : AppCompatActivity() {

    // okHttpClient
    private val mClient = getOkClient()
    private var mUrl = "https://publicobject.com/helloworld.txt"
    private val TAG = "peter"
    private var mRequest:Request?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_okhttp_test)
        requestPermission()
        intiView()
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

    private fun intiView() {
        mRequest = Request.Builder().url(mUrl).build()
        btnGet.setOnClickListener {
            doGetRequest()
        }
        btnPost.setOnClickListener {
            doPostRequest()
        }
        val popup = SearchPop(this)
        btnShowPop.setOnClickListener {
            popup.setBackgroundColor(Color.TRANSPARENT)
           // popup.setPopupGravityMode(BasePopupWindow.GravityMode.RELATIVE_TO_ANCHOR)
            popup.popupGravity = Gravity.BOTTOM
            popup.showPopupWindow(btnShowPop)
        }
    }

    private fun doGetRequest() {
        mRequest?.let {
            mClient.newCall(it).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.d(TAG, "fail e===${e.message}   cause=== ${e.cause}")
                }

                override fun onResponse(call: Call, response: Response) {
                    Log.d(TAG, " get success  response=== ${response.body.toString()}")
                    runOnUiThread {
                        tvResponseContent.text = response.toString()
                    }
                }

            })
        }

    }

    private fun doPostRequest() {
        val mPerson = Person(name = "张三", age = 18)
        val jsonData = "application/json; charset=utf-8".toMediaType()
        val mRequestBody = Gson().toJson(mPerson).toRequestBody(jsonData)
        // val mRequest = Request.Builder().url(mUrl).post(mRequestBody).build()
        mRequest?.let {
            mClient.newCall(it).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.d(TAG, " post fail")
                }

                override fun onResponse(call: Call, response: Response) {
                    Log.d(TAG, " post success  response=== ${response.body.toString()}")
                    runOnUiThread {
                        tvResponseContent.text = response.toString()
                    }

                }

            })
        }

    }

    private fun getOkClient(): OkHttpClient {
        return OkHttpClient.Builder().eventListener(OkEventListener()).build()
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