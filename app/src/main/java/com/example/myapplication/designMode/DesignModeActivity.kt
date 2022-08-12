package com.example.myapplication.designMode

import android.app.Activity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityDesignModeBinding
import com.example.myapplication.okHttp.util.LogUtil

/**
 * @Author qipeng
 * @Date 2022/8/11
 * @Desc
 */
class DesignModeActivity : Activity() {

    private lateinit var mBinding: ActivityDesignModeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         mBinding = DataBindingUtil.setContentView(this,R.layout.activity_design_mode )
        mBinding.btnOne.setOnClickListener {
            LogUtil.D(log = "one")
        }
        mBinding.btnTwo.setOnClickListener {
            LogUtil.D(log = "two")
        }
        mBinding.btnThree.setOnClickListener {
            LogUtil.D(log = "three")
        }

    }
}