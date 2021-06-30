package com.example.myapplication.okHttp

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.myapplication.R
import com.example.myapplication.okHttp.bean.Person
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_okhttp_test.*
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException
import java.util.logging.Logger

/**
 * @date 2021/6/30
 * @author qipeng
 * @desc okHttp请求
 */
class OkHttpTestActivity : AppCompatActivity() {

    // okHttpClient
    private val mClient = getOkClient()
    private var mUrl = "https://www.baidu.com"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_okhttp_test)
        requestPermission()
        intiView()
    }

    private fun requestPermission() {
       if (ContextCompat.checkSelfPermission(this,Manifest.permission.INTERNET)== PackageManager.PERMISSION_GRANTED){
           return
        }else{
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.INTERNET),0)
        }
    }

    private fun intiView() {
        btnGet.setOnClickListener {
            doGetRequest()
        }
        btnPost.setOnClickListener {
            doPostRequest()
        }
    }

    private fun doGetRequest() {
        val mRequest = Request.Builder().url(mUrl).build()
        mClient.newCall(mRequest).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.d("peter", "fail e===${e.message}   cause=== ${e.cause}")
            }

            override fun onResponse(call: Call, response: Response) {
                Log.d("peter", "success")
                runOnUiThread {
                    tvResponseContent.text = response.toString()
                }
            }

        })

    }

    private fun doPostRequest() {
        val mPerson = Person(name = "张三", age = 18)
        val jsonData = "application/json; charset=utf-8".toMediaType()
        val mRequestBody = Gson().toJson(mPerson).toRequestBody(jsonData)
        val mRequest = Request.Builder().url(mUrl).post(mRequestBody).build()
        mClient.newCall(mRequest).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.d("peter", " post fail")
            }

            override fun onResponse(call: Call, response: Response) {
                Log.d("peter", " post success")
                runOnUiThread {
                    tvResponseContent.text = response.toString()
                }
            }

        })

    }

    private fun getOkClient(): OkHttpClient {
        return OkHttpClient()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
       when(requestCode){
           0->{
                Log.d("peter","请求权限成功")
           }
       }
    }
}