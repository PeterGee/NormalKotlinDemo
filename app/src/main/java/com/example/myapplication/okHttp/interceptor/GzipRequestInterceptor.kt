package com.example.myapplication.okHttp.interceptor

import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.RequestBody
import okhttp3.Response
import okio.BufferedSink
import okio.GzipSink
import okio.buffer

/*
 * @date 2021/7/14
 * @author qipeng
 * @desc
 */
class GzipRequestInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originRequest = chain.request()
        if (originRequest.header("Content-Encoding") != null) {
            return chain.proceed(originRequest)
        }

        // compressedRequest
        val compressedRequest = originRequest.newBuilder()
            .header("Content-Encoding", "gzip")
            .method(originRequest.method, originRequest.body?.let { CustomRequestBody(it) })
            .build()
        return chain.proceed(compressedRequest)

    }
}

class CustomRequestBody(body: RequestBody) : RequestBody() {
    private val mBody = body
    override fun contentType(): MediaType? {
        return mBody.contentType()
    }

    override fun writeTo(sink: BufferedSink) {
        val gzipSink = GzipSink(sink).buffer()
        mBody.writeTo(gzipSink)
        gzipSink.close()
    }

    override fun contentLength(): Long {
        return -1
    }

}