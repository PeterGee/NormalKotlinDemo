package com.example.myapplication.coroutine

import android.app.Activity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityMulityDialogBinding

/**
 * @Author qipeng
 * @Date 2024/6/1
 * @Desc multy
 */
class MultiDialogDemoActivity : Activity() {
    private var mBinding: ActivityMulityDialogBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_mulity_dialog)

        initListener()
    }

    private fun initListener() {
        mBinding?.btnShowDialog?.setOnClickListener{

        }
    }

}