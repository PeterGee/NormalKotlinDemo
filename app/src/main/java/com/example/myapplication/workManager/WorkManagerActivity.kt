package com.example.myapplication.workManager

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.work.*
import com.example.myapplication.R
import kotlinx.android.synthetic.main.activity_workmanager.*
import java.util.concurrent.TimeUnit

/**
 * @date 2019/11/18
 * @author qipeng
 * @desc workManager
 */
class WorkManagerActivity : AppCompatActivity() {
    val TAG = javaClass.simpleName
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_workmanager)
        initView()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun initView() {
        val workOne = OneTimeWorkRequest.Builder(SubWorkerOne::class.java).build()
        val workTwo = OneTimeWorkRequest.Builder(SubWorkerTwo::class.java).build()
        val workThree = OneTimeWorkRequest.Builder(SubWorkerTwo::class.java)
            .setInitialDelay(100, TimeUnit.SECONDS).build()

        val mConstraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED) // 指定网络连接状态进行调用
            .setRequiresBatteryNotLow(true)  // 指定电池电量状态
            .setRequiresDeviceIdle(true)     // 指定设备空闲状态是否执行任务
            .setRequiresCharging(false)  // 是否需要充电状态
            .setTriggerContentMaxDelay(1000, TimeUnit.SECONDS) // 指定延迟时间
            .build()
        val workConstraint =
            OneTimeWorkRequest.Builder(SubWorkerTwo::class.java).setConstraints(mConstraints)
                .build()

        // workManagerOne
        tv_work_manager_one.setOnClickListener {
            WorkManager.getInstance().enqueue(workOne)
        }

        // workManagerTwo
        tv_work_manager_two.setOnClickListener {
            // WorkManager.getInstance().enqueue(workTwo)
            val status = WorkManager.getInstance().getWorkInfoById(workTwo.id)
            // 监听数据变化
            if (status.isDone) {
                Log.d(TAG, "data is success")
            }
            WorkManager.getInstance().enqueue(workTwo)
        }

        tv_work_manager_three.setOnClickListener {
            Log.d("peter", "delayWorkStart")

            val status = WorkManager.getInstance().getWorkInfoById(workThree.id)
            // 监听数据变化
            if (status.isDone) {
                Log.d(TAG, "workThree is success")
            }
            WorkManager.getInstance().enqueue(workThree)
        }


        // 添加约束
        tv_work_manager_period_request.setOnClickListener {

            val constraintStatus =
                WorkManager.getInstance().getWorkInfoByIdLiveData(workConstraint.id)
            constraintStatus.observe(this, Observer {
                // 打印constraintStatus状态
                it?.state?.name?.let { it1 -> Log.d(TAG, it1) }
                if (it.state.isFinished) {
                    Log.d(TAG, "isFinished")
                }
            })
            WorkManager.getInstance().enqueue(workConstraint)

            // 通过指定id来取消任务
            WorkManager.getInstance().cancelWorkById(workConstraint.id)
            // 取消所有任务
            WorkManager.getInstance().cancelAllWork()
            // 通过TAG来取消任务  注意：前提是已经设置了tag
            WorkManager.getInstance().cancelAllWorkByTag("TAG")
            // 取消所有该TAG的任务
            WorkManager.getInstance().cancelAllWorkByTag("TAG")
        }

        // 按顺序执行
        tv_work_manager_in_turn.setOnClickListener {
            WorkManager.getInstance().beginWith(workOne).then(workTwo).enqueue()
        }

        // 任务唯一性
        tv_work_manager_unique.setOnClickListener {
            WorkManager.getInstance().beginUniqueWork("unique", ExistingWorkPolicy.APPEND, workOne)
                .enqueue()
        }
    }
}