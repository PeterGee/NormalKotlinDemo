package com.example.myapplication.deepseek

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.widget.Toast
import com.example.myapplication.databinding.ActivityDeepSeekTestBinding
import com.example.myapplication.widget.MockEventStreamProcessor

/**
 * @Author qipeng
 * @Date 2025/3/14
 * @Desc
 */
class DeepSeekTestActivity : Activity() {
    private lateinit var mBinding: ActivityDeepSeekTestBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityDeepSeekTestBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        val mockProcessor = MockEventStreamProcessor(mBinding.deepSeekTv)

        mBinding.startButton.setOnClickListener {
            mockProcessor.setTypingSpeed(150)
            mockProcessor.startProcessing()
            // 开启点击监听
            mBinding.deepSeekTv.movementMethod = LinkMovementMethod.getInstance()
        }
        mBinding.clearButton.setOnClickListener {
            mBinding.deepSeekTv.clear()
            val newContent = "这是新的文本内容..."
            mBinding.deepSeekTv.appendContent(newContent)
        }

        mBinding.copyButton.setOnClickListener {
            val textToCopy = mBinding.deepSeekTv.text.toString()
            if (textToCopy.isEmpty()) {
                Toast.makeText(this, "没有可复制的内容", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("Copied Text", textToCopy)
            clipboard.setPrimaryClip(clip)
            Toast.makeText(this, "已复制到剪贴板", Toast.LENGTH_SHORT).show()
        }
    }
}