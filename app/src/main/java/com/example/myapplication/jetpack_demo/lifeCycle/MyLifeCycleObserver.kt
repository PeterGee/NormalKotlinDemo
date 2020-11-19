package com.example.myapplication.jetpack_demo.lifeCycle

import android.content.Context
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

/**
 * @date 2020/3/30
 * @author qipeng
 * @desc lifeCycleObserver
 */
class MyLifeCycleObserver : LifecycleObserver {
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onConnectListener() {

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onDisConnectListener() {

    }

}

internal class MyLocationListener(
    private val context: Context,
    private val lifeCycle: Lifecycle
) {
    private var enable: Boolean = false
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun start() {
        if (enable) {

        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun stop() {

    }

    fun enable() {
        enable = true
        if (lifeCycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {

        }

    }
}