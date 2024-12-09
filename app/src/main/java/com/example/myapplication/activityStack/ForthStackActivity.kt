package com.example.myapplication.activityStack

import android.os.Bundle
import com.example.myapplication.databinding.ActivityFirstStackBinding

/**
 * @Author qipeng
 * @Date 2024/12/9
 * @Desc
 */
class ForthStackActivity:BaseStackActivity() {
    private lateinit var mBinding: ActivityFirstStackBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityFirstStackBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        initView()
    }

    private fun initView() {

    }

    override fun onBackPressed() {
        super.onBackPressed()
        backToFirstActivity()
    }
}