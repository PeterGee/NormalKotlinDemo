package com.example.myapplication.activityStack

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

/**
 * @Author qipeng
 * @Date 2024/12/9
 * @Desc  baseStackActivity
 */
abstract class BaseStackActivity:AppCompatActivity() {

    companion object {
        val mActivityList: MutableList<Activity> = mutableListOf()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivityList.add(this)
        Log.d("activityStack","onCreate:${this.javaClass.simpleName}  size：${mActivityList.size}")
    }

    override fun onDestroy() {
        super.onDestroy()
        mActivityList.remove(this)
        Log.d("activityStack","onDestroy:${this.javaClass.simpleName}  size：${mActivityList.size}")
    }

    fun finishAll(){
        Log.d("activityStack","finishAll   size:${mActivityList.size}")
        for (activity in mActivityList){
            Log.d("activityStack","finishAll:${activity.javaClass.simpleName}")
            if (!activity.isFinishing){
                activity.finish()
            }
        }
        mActivityList.clear()

    }

    fun backToFirstActivity(){
        Log.d("activityStack","backToFirstActivity   size:${mActivityList.size}")
        for (activity in mActivityList){
            if (activity.javaClass.simpleName == "FirstStackActivity"){
                continue
            }
            if (!activity.isFinishing){
                activity.finish()
            }
        }
    }


}