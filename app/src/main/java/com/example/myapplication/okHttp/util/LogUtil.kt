package com.example.myapplication.okHttp.util

import android.util.Log

/*
 * @date 2021/7/8 
 * @author qipeng
 * @desc
 */
object LogUtil {

    fun D(tag:String?="tag",log: String){
        Log.d(tag,log)
    }

    fun E(tag:String?="tag",log: String){
        Log.e(tag,log)
    }
}