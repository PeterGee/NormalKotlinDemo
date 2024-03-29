package com.example.myapplication.okHttp.event

import android.util.Log
import okhttp3.*
import java.io.IOException
import java.net.InetAddress
import java.net.InetSocketAddress
import java.net.Proxy
import java.util.concurrent.atomic.AtomicLong

/**
 * @date 2021/7/6
 * @author qipeng
 * @desc
 */
class OkEventFactoryListener(callId:Long,startTime:Long) : EventListener() {

    private val TAG = "factory"
    private var mCallStartNanos: Long = startTime
    private val mCallId=callId

    override fun callStart(call: Call) {
        super.callStart(call)
        printEvent("callStart")
    }

    override fun callEnd(call: Call) {
        super.callEnd(call)
        printEvent("callEnd")
    }

    override fun dnsStart(call: Call, domainName: String) {
        super.dnsStart(call, domainName)
        printEvent("dnsStart")
    }

    override fun dnsEnd(call: Call, domainName: String, inetAddressList: List<InetAddress>) {
        super.dnsEnd(call, domainName, inetAddressList)
        printEvent("dnsEnd")
    }

    override fun connectStart(call: Call, inetSocketAddress: InetSocketAddress, proxy: Proxy) {
        super.connectStart(call, inetSocketAddress, proxy)
        printEvent("connectStart")
    }

    override fun connectEnd(
        call: Call,
        inetSocketAddress: InetSocketAddress,
        proxy: Proxy,
        protocol: Protocol?
    ) {
        super.connectEnd(call, inetSocketAddress, proxy, protocol)
        printEvent("connectEnd")
    }

    override fun connectionReleased(call: Call, connection: Connection) {
        super.connectionReleased(call, connection)
        printEvent("connectionReleased")
    }

    override fun connectFailed(
        call: Call,
        inetSocketAddress: InetSocketAddress,
        proxy: Proxy,
        protocol: Protocol?,
        ioe: IOException
    ) {
        super.connectFailed(call, inetSocketAddress, proxy, protocol, ioe)
        printEvent("connectFailed")
    }

    override fun requestHeadersStart(call: Call) {
        super.requestHeadersStart(call)
        printEvent("requestHeadersStart")
    }

    override fun requestHeadersEnd(call: Call, request: Request) {
        super.requestHeadersEnd(call, request)
        printEvent("requestHeadersEnd")
    }

    override fun requestBodyStart(call: Call) {
        super.requestBodyStart(call)
        printEvent("requestBodyStart")
    }

    override fun requestBodyEnd(call: Call, byteCount: Long) {
        super.requestBodyEnd(call, byteCount)
        printEvent("requestBodyEnd")
    }

    override fun requestFailed(call: Call, ioe: IOException) {
        super.requestFailed(call, ioe)
        printEvent("requestBodyFailed")
    }


    override fun responseHeadersStart(call: Call) {
        super.responseHeadersStart(call)
        printEvent("responseHeadersStart")
    }

    override fun responseHeadersEnd(call: Call, response: Response) {
        super.responseHeadersEnd(call, response)
        printEvent("responseHeadersEnd")
    }

    override fun responseBodyStart(call: Call) {
        super.responseBodyStart(call)
        printEvent("responseBodyStart")
    }

    override fun responseBodyEnd(call: Call, byteCount: Long) {
        super.responseBodyEnd(call, byteCount)
        printEvent("responseBodyEnd")
    }

    private fun printEvent(name: String) {
        val elapsedTime: Long = System.currentTimeMillis() - mCallStartNanos
        mLog("callId== $mCallId  time=== $elapsedTime ms name=== $name")
    }

    private fun mLog(str: String) {
        Log.d(TAG, str)
    }

    companion object {
        private val nextCallId: AtomicLong = AtomicLong(1L)
        fun create(): Factory {
            return object : Factory {
                override fun create(call: Call): EventListener {
                    val callId: Long = nextCallId.getAndIncrement()
                    Log.d("factory", "callId==  $callId  url==${call.request().url}")
                    return OkEventFactoryListener(callId,System.currentTimeMillis())
                }

            }
        }
    }

}