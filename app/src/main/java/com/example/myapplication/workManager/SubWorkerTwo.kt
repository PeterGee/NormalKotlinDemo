package com.example.myapplication.workManager

import android.content.Context
import android.util.Log
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters

/**
 * @date 2019/11/18
 * @author qipeng
 * @desc worker子类
 */
class SubWorkerTwo(context: Context, workerParameters: WorkerParameters) :
    Worker(context, workerParameters) {
    val TAG: String = javaClass.simpleName
    override fun doWork(): Result {
        Log.d(TAG, "doWork  携带数据")
        val data = Data.Builder().putString("data", "workManagerWithParameter").build()
        return Result.success(data)
    }
}