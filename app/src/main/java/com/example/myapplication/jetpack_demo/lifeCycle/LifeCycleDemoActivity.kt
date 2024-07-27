package com.example.myapplication.jetpack_demo.lifeCycle

import android.app.Activity
import android.os.Bundle
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleRegistry

/**
 * @date 2020/3/30
 * @author qipeng
 * @desc lifeCycle demo
 */
class LifeCycleDemoActivity : Activity() {
    private lateinit var lifecycleRegistry: LifecycleRegistry

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 将生命周期与lifeCycle进行关联
        // lifecycleRegistry = LifecycleRegistry(this)
        lifecycleRegistry.markState(Lifecycle.State.CREATED)
    }

    override fun onStart() {
        super.onStart()
        lifecycleRegistry.markState(Lifecycle.State.STARTED)
    }

    fun getLifecycle(): Lifecycle {
        return lifecycleRegistry
    }

}