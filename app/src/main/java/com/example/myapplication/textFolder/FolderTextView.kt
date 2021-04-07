package com.example.myapplication.textFolder

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.text.*
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.example.myapplication.R

/**
 * 结尾带“查看全部”的TextView，点击可以展开文字，展开后可收起。
 * 目前存在一个问题：外部调用setText()时会造成界面该TextView下方的View抖动；
 * 可以先调用getFullText()，当已有文字和要设置的文字不一样才调用setText()，可降低抖动的次数；
 * 通过在onMeasure()中设置高度已经修复了该问题了。
 */
class FolderTextView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
        androidx.appcompat.widget.AppCompatTextView(context, attrs, defStyleAttr) {
    // 收起文字
    private var mFoldText: String?

    // 展开文字
    private var mUnFoldText: String?

    // 固定行数
    private var mFoldLine: Int

    // 尾部文字颜色
    private var mTailColor: Int

    // 是否可以再次收起
    private var mCanFoldAgain = false

    // 收缩状态
    private var mIsFold = false

    // 绘制，防止重复进行绘制
    private var mHasDrawn = false

    // 内部绘制
    private var mIsInner = false

    /**
     * 获取全文本
     *
     * @return 全文本
     */
    // 全文本
    var fullText: String? = null

    // 行间距倍数
    private var mLineSpacingMultiplier = 1.0f

    // 行间距额外像素
    private var mLineSpacingExtra = 0.0f

    // 统计使用二分法裁剪源文本的次数
    private var mCountBinary = 0

    // 统计使用备用方法裁剪源文本的次数
    private var mCountBackUp = 0

    // 统计onDraw调用的次数
    private var mCountOnDraw = 0


    init {
        val a = context.obtainStyledAttributes(attrs, R.styleable.FolderTextView)
        mFoldText = a.getString(R.styleable.FolderTextView_foldText)
        if (null == mFoldText) {
            mFoldText =DEFAULT_FOLD_TEXT
        }
        mUnFoldText = a.getString(R.styleable.FolderTextView_unFoldText)
        if (null == mUnFoldText) {
            mUnFoldText = DEFAULT_UNFOLD_TEXT
        }
        mFoldLine = a.getInt(R.styleable.FolderTextView_foldLine,DEFAULT_FOLD_LINE)
        if (mFoldLine < 1) {
            throw RuntimeException("foldLine must not less than 1")
        }
        mTailColor = a.getColor(R.styleable.FolderTextView_tailTextColor, DEFAULT_TAIL_TEXT_COLOR)
        mCanFoldAgain = a.getBoolean(R.styleable.FolderTextView_canFoldAgain, DEFAULT_CAN_FOLD_AGAIN)
        a.recycle()
    }

    // 点击处理
    private val clickSpan: ClickableSpan = object : ClickableSpan() {
        override fun onClick(widget: View) {
            Log.d(TAG,"点击尾部标签")
           //  mIsFold = !mIsFold
            mHasDrawn = false
            invalidate()
        }

        override fun updateDrawState(ds: TextPaint) {
            ds.color = mTailColor
        }
    }

    override fun setText(text: CharSequence, type: BufferType) {
        if (TextUtils.isEmpty(fullText) || !mIsInner) {
            mHasDrawn = false
            fullText = text.toString()
        }
        super.setText(text, type)
    }

    override fun setLineSpacing(extra: Float, multiplier: Float) {
        mLineSpacingExtra = extra
        mLineSpacingMultiplier = multiplier
        super.setLineSpacing(extra, multiplier)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        // 必须解释下：由于为了得到实际一行的宽度（makeTextLayout()中需要使用），必须要先把源文本设置上，然后再裁剪至指定行数；
        // 这就导致了该TextView会先布局一次高度很高（源文本行数高度）的布局，裁剪后再次布局成指定行数高度，因而下方View会抖动；
        // 这里的处理是，super.onMeasure()已经计算出了源文本的实际宽高了，取出指定行数的文本再次测量一下其高度，
        // 然后把这个高度设置成最终的高度就行了！
        if (!mIsFold) {
            var layout = layout
            val line = foldLine
            if (line < layout.lineCount) {
                val index = layout.getLineEnd(line - 1)
                if (index > 0) {
                    // 得到一个字符串，该字符串恰好占据mFoldLine行数的高度
                    val strWhichHasExactlyFoldLine = text.subSequence(0, index).toString()
                    Log.d(TAG, "strWhichHasExactlyFoldLine-->$strWhichHasExactlyFoldLine")
                    layout = makeTextLayout(strWhichHasExactlyFoldLine)
                    // 把这个高度设置成最终的高度，这样下方View就不会抖动了
                    setMeasuredDimension(measuredWidth, layout.height + paddingTop + paddingBottom)
                }
            }
        }
    }

    override fun onDraw(canvas: Canvas) {
        Log.d(FolderTextView.Companion.TAG, "onDraw() " + mCountOnDraw++ + ", getMeasuredHeight() " + measuredHeight)
        if (!mHasDrawn) {
            resetText()
        }
        super.onDraw(canvas)
        mHasDrawn = true
        mIsInner = false
    }
    /**
     * 获取折叠文字
     */
    var foldText: String?
        get() = mFoldText
        set(foldText) {
            mFoldText = foldText
            invalidate()
        }
    /**
     * 设置展开文字
     */
    var unFoldText: String?
        get() = mUnFoldText
        set(unFoldText) {
            mUnFoldText = unFoldText
            invalidate()
        }
    /**
     * 设置折叠行数
     */
    var foldLine: Int
        get() = mFoldLine
        set(foldLine) {
            mFoldLine = foldLine
            invalidate()
        }
    /**
     * 设置尾部文字颜色
     */
    var tailColor: Int
        get() = mTailColor
        set(tailColor) {
            mTailColor = tailColor
            invalidate()
        }
    /**
     *设置是否可以再次折叠
     */
    var isCanFoldAgain: Boolean
        get() = mCanFoldAgain
        set(canFoldAgain) {
            mCanFoldAgain = canFoldAgain
            invalidate()
        }

    /**
     * 获取TextView的Layout，注意这里使用getWidth()得到宽度
     * @param text 源文本
     * @return Layout
     */
    private fun makeTextLayout(text: String?): Layout {
        return StaticLayout(text, paint, width - paddingLeft - paddingRight, Layout.Alignment.ALIGN_NORMAL, mLineSpacingMultiplier, mLineSpacingExtra, true)
    }

    /**
     * 重置文字
     */
    private fun resetText() {
        // 文字本身就小于固定行数的话，不添加尾部的收起/展开文字
        val layout = makeTextLayout(fullText)
        if (layout.lineCount <= foldLine) {
            text = fullText
            return
        }
        var spanStr = SpannableString(fullText)
        if (mIsFold) {
            // 收缩状态
            if (mCanFoldAgain) {
                spanStr = createUnFoldSpan(fullText)
            }
        } else {
            // 展开状态
            spanStr = createFoldSpan(fullText)
        }
        updateText(spanStr)
        movementMethod = LinkMovementMethod.getInstance()
    }

    /**
     * 不更新全文本下，进行展开和收缩操作
     *
     * @param text 源文本
     */
    private fun updateText(text: CharSequence) {
        mIsInner = true
        setText(text)
    }

    /**
     * 创建展开状态下的Span
     *
     * @param text 源文本
     * @return 展开状态下的Span
     */
    private fun createUnFoldSpan(text: String?): SpannableString {
        val destStr = text + mFoldText
        val start = destStr.length - mFoldText!!.length
        val end = destStr.length
        val spanStr = SpannableString(destStr)
        spanStr.setSpan(clickSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        return spanStr
    }

    /**
     * 创建收缩状态下的Span
     *
     * @param text
     * @return 收缩状态下的Span
     */
    private fun createFoldSpan(text: String?): SpannableString {
        val startTime = System.currentTimeMillis()
        val destStr = tailorText(text)
        Log.d(TAG, (System.currentTimeMillis() - startTime).toString() + "ms")
        val start = destStr.length - mUnFoldText!!.length
        val end = destStr.length
        val spanStr = SpannableString(destStr)
        spanStr.setSpan(clickSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        return spanStr
    }

    /**
     * 裁剪文本至固定行数（备用方法）
     *
     * @param text 源文本
     * @return 裁剪后的文本
     */
    private fun tailorTextBackUp(text: String?): String {
        Log.d(TAG, "使用备用方法: tailorTextBackUp() " + mCountBackUp++)
        val destStr = text + DEFAULT_ELLIPSIZE + mUnFoldText
        val layout = makeTextLayout(destStr)

        // 如果行数大于固定行数
        return if (layout.lineCount > foldLine) {
            var index = layout.getLineEnd(foldLine - 1)
            if (text!!.length < index) {
                index = text.length
            }
            // 从最后一位逐渐试错至固定行数（可以考虑用二分法改进）
            if (index <= 1) {
                return DEFAULT_ELLIPSIZE + mUnFoldText
            }
            val subText = text.substring(0, index - 1)
            tailorText(subText)
        } else {
            destStr
        }
    }

    /**
     * 裁剪文本至固定行数（二分法）。经试验，在文字长度不是很长时，效率比备用方法高不少；当文字长度过长时，备用方法则优势明显。
     *
     * @param text 源文本
     * @return 裁剪后的文本
     */
    private fun tailorText(text: String?): String {
        // return tailorTextBackUp(text);
        var start = 0
        var end = text!!.length - 1
        var mid = (start + end) / 2
        var find = finPos(text, mid)
        while (find != 0 && end > start) {
            Log.d(FolderTextView.Companion.TAG, "使用二分法: tailorText() " + mCountBinary++)
            if (find > 0) {
                end = mid - 1
            } else if (find < 0) {
                start = mid + 1
            }
            mid = (start + end) / 2
            find = finPos(text, mid)
        }
        Log.d(FolderTextView.Companion.TAG, "mid is: $mid")
        val ret: String
        ret = if (find == 0) {
            text.substring(0, mid) + FolderTextView.Companion.DEFAULT_ELLIPSIZE + mUnFoldText
        } else {
            tailorTextBackUp(text)
        }
        return ret
    }

    /**
     * 查找一个位置P，到P时为mFoldLine这么多行，加上一个字符‘A’后则刚好为mFoldLine+1这么多行
     *
     * @param text 源文本
     * @param pos  位置
     * @return 查找结果
     */
    private fun finPos(text: String?, pos: Int): Int {
        val destStr = text!!.substring(0, pos) +DEFAULT_ELLIPSIZE + mUnFoldText
        val layout = makeTextLayout(destStr)
        val layoutMore = makeTextLayout(destStr + "A")
        val lineCount = layout.lineCount
        val lineCountMore = layoutMore.lineCount
        return if (lineCount == foldLine && lineCountMore == foldLine + 1) {
            // 行数刚好到折叠行数
            0
        } else if (lineCount > foldLine) {
            // 行数比折叠行数多
            1
        } else {
            // 行数比折叠行数少
            -1
        }
    }

    companion object {
        // TAG
        private const val TAG = "peter"
        // 默认打点文字
        private const val DEFAULT_ELLIPSIZE = "..."
        // 默认收起文字
        private const val DEFAULT_FOLD_TEXT = "[收起]"
        // 默认展开文字
        private const val DEFAULT_UNFOLD_TEXT = "[查看全部]"
        // 默认固定行数
        private const val DEFAULT_FOLD_LINE = 2
        // 默认收起和展开文字颜色
        private const val DEFAULT_TAIL_TEXT_COLOR = Color.GRAY
        // 默认是否可以再次收起
        private const val DEFAULT_CAN_FOLD_AGAIN = true
    }

}