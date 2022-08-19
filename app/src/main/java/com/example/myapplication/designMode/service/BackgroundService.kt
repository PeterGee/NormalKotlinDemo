package com.example.myapplication.designMode.service

import android.app.Service
import android.content.Intent
import android.content.ServiceConnection
import android.os.Binder
import android.os.IBinder
import com.example.myapplication.okHttp.util.LogUtil

/**
 * @Author qipeng
 * @Date 2022/8/19
 * @Desc
 */
class BackgroundService : Service() {

    private val localBinder = MyBinder()
    override fun onBind(intent: Intent?): IBinder {
        LogUtil.D(log = "service onBind")
        return localBinder
    }

    override fun unbindService(conn: ServiceConnection) {
        LogUtil.D(log = "service unBind")
        super.unbindService(conn)
    }

    override fun onCreate() {
        super.onCreate()
        LogUtil.D(log = "service onCreate")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        LogUtil.D(log = "onStartCommand")
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        LogUtil.D(log = "onDestroy")
    }


    open class MyBinder : Binder() {
        open fun getService(): BackgroundService {
            return BackgroundService()
        }


    }

      fun printLog(){
        LogUtil.D(log = "printLlog")
    }


}