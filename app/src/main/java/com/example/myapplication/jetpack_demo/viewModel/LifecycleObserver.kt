package com.example.myapplication.jetpackdemo

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

/**
 * @date 2020/10/10
 * @author qipeng
 * @desc
 */
class LifecycleObserver :LifecycleObserver{

    val tag="lifecycleObserver"
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume(){
        Log.d(tag,"onResume")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onPause(){
        Log.d(tag,"onPause")
    }

}