package com.example.myapplication.designMode

import android.app.Activity
import android.app.Service
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import androidx.databinding.DataBindingUtil
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityDesignModeBinding
import com.example.myapplication.designMode.service.BackgroundService
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
           val intent=Intent(this,BackgroundService::class.java)
            startService(intent)
        }
        mBinding.btnTwo.setOnClickListener {
            val intent=Intent(this,BackgroundService::class.java)
            stopService(intent)
        }
        mBinding.btnThree.setOnClickListener {
            val intent=Intent(this,BackgroundService::class.java)
            bindService(intent,conn,Service.BIND_AUTO_CREATE)
        }

        mBinding.btnFour.setOnClickListener {
            unbindService(conn)
        }


    }

   private val conn=object :ServiceConnection{
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            LogUtil.D(log = "service onServiceConnected")
            val mBinder=service as BackgroundService.MyBinder
            mBinder.getService().printLog()
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            LogUtil.D(log = "service onServiceDisconnected")
        }

    }
}