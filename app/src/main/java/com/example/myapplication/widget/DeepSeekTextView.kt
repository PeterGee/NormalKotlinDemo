package com.example.myapplication.widget

/**
 * @Author qipeng
 * @Date 2025/3/14
 * @Desc
 */
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.TextPaint
import android.text.method.ScrollingMovementMethod
import android.text.style.AlignmentSpan
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.ScrollView
import androidx.appcompat.widget.AppCompatTextView

class DeepSeekTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    private val mainHandler = Handler(Looper.getMainLooper())
    private val contentBuilder = SpannableStringBuilder()
    private val tempContentList = mutableListOf<String>()
    private var currentLineStart = 0
    private val normalTextColor = Color.parseColor("#8A9098") // 非加粗文本颜色

    init {
        movementMethod = ScrollingMovementMethod()
        highlightColor = resources.getColor(android.R.color.transparent, null)
        isClickable = true
        isFocusable = true
        isLongClickable = false
    }

    fun appendContent(content: String) {
        mainHandler.post {
            tempContentList.add(content)
            val combinedContent = tempContentList.joinToString("")
            processContent(combinedContent)
        }
    }

    private fun processContent(content: String) {
        contentBuilder.clear()
        var isBold = false
        var startIndex = 0
        for (i in content.indices) {
            if (i <= content.length - 2 && content[i] == '*' && content[i + 1] == '*') {
                if (isBold) {
                    // 结束加粗
                    val boldText = content.substring(startIndex, i)
                    addTextWithStyle(boldText, true)
                    isBold = false
                    startIndex = i + 2
                } else {
                    // 开始加粗
                    val normalText = content.substring(startIndex, i)
                    addTextWithStyle(normalText, false)
                    isBold = true
                    startIndex = i + 2
                }
            }
        }
        // 处理最后一段文本
        val lastText = content.substring(startIndex)
        addTextWithStyle(lastText, isBold)

        // 处理 Markdown 链接
        handleMarkdownLinks()

        // 处理换行
        if (content.contains("\n")) {
            handleNewLine()
        }

        text = contentBuilder
        (parent as? ScrollView)?.let { scrollView ->
            scrollView.post {
                scrollView.fullScroll(ScrollView.FOCUS_DOWN)
            }
        }
    }

    private fun addTextWithStyle(text: String, isBold: Boolean) {
        if (text.isNotEmpty()) {
            val start = contentBuilder.length
            contentBuilder.append(text)
            if (isBold) {
                contentBuilder.setSpan(
                    BoldAndBlackSpan(),
                    start,
                    contentBuilder.length,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            } else {
                contentBuilder.setSpan(
                    ForegroundColorSpan(normalTextColor),
                    start,
                    contentBuilder.length,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
        }
    }

    private fun handleMarkdownLinks() {
        val text = contentBuilder.toString()
        val linkPattern = "\\[(.*?)\\]\\((.*?)\\)".toRegex()
        val matches = linkPattern.findAll(text)
        for (match in matches) {
            val linkText = match.groupValues[1]
            val linkUrl = match.groupValues[2]
            val start = match.range.first
            val end = match.range.last + 1

            // 删除原 Markdown 格式
            contentBuilder.delete(start, end)

            // 插入链接文本
            contentBuilder.insert(start, linkText)

            // 设置点击事件
            contentBuilder.setSpan(
                object : ClickableSpan() {
                    override fun onClick(widget: View) {
                        val intent = Intent(Intent.ACTION_VIEW)
                        intent.data = Uri.parse(linkUrl)
                        context.startActivity(intent)
                    }

                    override fun updateDrawState(ds: TextPaint) {
                        super.updateDrawState(ds)
                        ds.color = Color.BLUE
                        ds.isUnderlineText = true
                    }
                },
                start,
                start + linkText.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
    }

    private fun handleNewLine() {
        val lineBreaks = contentBuilder.toString().split("\n").size - 1
        if (lineBreaks > 0) {
            currentLineStart = contentBuilder.length - lineBreaks
            contentBuilder.setSpan(
                AlignmentSpan.Standard(android.text.Layout.Alignment.ALIGN_NORMAL),
                currentLineStart,
                contentBuilder.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
    }

    fun clear() {
        mainHandler.post {
            contentBuilder.clear()
            tempContentList.clear()
            text = ""
        }
    }

    private class BoldAndBlackSpan : StyleSpan(Typeface.BOLD) {
        override fun updateDrawState(ds: TextPaint) {
            super.updateDrawState(ds)
            ds.color = Color.BLACK
            ds.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        }
    }

    // 重写 onTouchEvent 方法，确保点击事件传递到 ClickableSpan
    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                // 处理点击按下事件
                val x = event.x.toInt()
                val y = event.y.toInt()
                val layout = layout
                val line = layout.getLineForVertical(y)
                val offset = layout.getOffsetForHorizontal(line, x.toFloat())
                val spans = contentBuilder.getSpans(offset, offset, ClickableSpan::class.java)
                if (spans.isNotEmpty()) {
                    spans[0].onClick(this)
                    return true
                }
            }
            else -> {
                // 其他事件由父类处理
            }
        }
        return super.onTouchEvent(event)
    }

    // 重写 performLongClick 方法，禁止长按
    override fun performLongClick(): Boolean {
        return false
    }
}
