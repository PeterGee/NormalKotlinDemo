package com.example.myapplication.widget

/**
 * @Author qipeng
 * @Date 2025/3/14
 * @Desc
 */
import android.os.Handler
import android.os.Looper
import org.json.JSONObject

class MockEventStreamProcessor(private val textView: DeepSeekTextView) {

    private val handler = Handler(Looper.getMainLooper())
    private val mockData = MockData.data

    private var typingSpeed = 100L // 默认打字速度，单位为毫秒

    fun startProcessing() {
        var index = 0

        val runnable = object : Runnable {
            override fun run() {
                if (index < mockData.size) {
                    val dataLine = mockData[index]
                    handleData(dataLine)
                    index++
                    handler.postDelayed(this, typingSpeed)
                } else {
                    // 处理完成事件
                    textView.appendContent("")
                }
            }
        }

        handler.post(runnable)
    }

    private fun handleData(data: String) {
        try {
            val lines = data.split("\n")
            if (lines.size == 2 && lines[0].startsWith("event:") && lines[1].startsWith("data:")) {
                val eventType = lines[0].substring(6)
                val dataJson = JSONObject(lines[1].substring(5))
                val content = dataJson.getString("content")

                when (eventType) {
                    "thinkingProcess" -> textView.appendContent(content)
                    "finalResult" -> textView.appendContent(content)
                    "done" -> handler.removeCallbacksAndMessages(null)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // 设置打字速度的方法
    fun setTypingSpeed(speed: Long) {
        this.typingSpeed = speed
    }
}
