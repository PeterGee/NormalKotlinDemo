package com.example.myapplication.okHttp.interceptor

import okhttp3.Interceptor
import okhttp3.Response

/*
 * @date 2021/7/13 
 * @author qipeng
 * @desc
 */
class CacheAgeInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val mOriginRequest = chain.request()
        val mOriginResponse = chain.proceed(mOriginRequest)
        // setMaxCacheAge
        return mOriginResponse.newBuilder().header("Cache-Control", "max-age=60").build()
    }
}