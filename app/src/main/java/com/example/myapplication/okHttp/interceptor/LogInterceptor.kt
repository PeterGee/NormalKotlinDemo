package com.example.myapplication.okHttp.interceptor

import com.example.myapplication.okHttp.util.LogUtil
import okhttp3.Interceptor
import okhttp3.Response

/*
 * @date 2021/7/8 
 * @author qipeng
 * @desc
 */
class LogInterceptor:Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val mRequest=chain.request()

        // startTime
        val startTime=System.currentTimeMillis()
        LogUtil.D(log = "Sending request url== ${mRequest.url} " +
                " connection== ${chain.connection()} " +
                " headers==${mRequest.headers}")

        var mResponse=chain.proceed(mRequest)
          //   mResponse=chain.proceed(mRequest)

        val endTime=System.currentTimeMillis()
        LogUtil.D(log="Received response url== ${mResponse.request.url} " +
                " costTime== ${endTime-startTime}ms"+
                " header== ${mResponse.headers}")

        return mResponse
    }
}