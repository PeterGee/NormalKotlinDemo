package com.example.myapplication.timerTest

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityTimerTestBinding
import java.util.Timer
import java.util.TimerTask

/**
 * @Author qipeng
 * @Date 2023/11/7
 * @Desc
 */
class TimerTestActivity:AppCompatActivity() {
    private var mDataBinding:ActivityTimerTestBinding?=null
    private var mFirstTimer:Timer?=null
    private var mSecondTimer:Timer?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mDataBinding = DataBindingUtil.setContentView(this,R.layout.activity_timer_test)
        initView()
    }

    private fun initView() {
       mDataBinding?.btnFirst?.setOnClickListener {
           mFirstTimer = Timer()
           mFirstTimer?.scheduleAtFixedRate(object :TimerTask(){
               override fun run() {
                  Log.d("tag","firstTimer start")
               }

           },0,500)
       }

      mDataBinding?.btnSecond?.setOnClickListener {
          mSecondTimer = Timer()
          mSecondTimer?.schedule(object :TimerTask(){
             override fun run() {
                 Log.d("tag","secondTimer start")
                 mFirstTimer?.apply {
                     // 取消timer1
                     cancel()
                 }
                 Log.d("tag", "firstTimer== $mFirstTimer")
             }

         },0)
      }
    }
}