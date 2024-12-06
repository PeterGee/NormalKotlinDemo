package com.example.myapplication.widget

/**
 * @Author qipeng
 * @Date 2024/12/6
 * @Desc
 */
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import android.util.AttributeSet
import android.widget.ProgressBar
import com.example.myapplication.R
import com.example.myapplication.util.DensityUtil

class NumberProgress @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = android.R.attr.progressBarStyleHorizontal
) : ProgressBar(context, attrs, defStyleAttr) {

    private var textPadding = DensityUtil.dip2px(context, 8f)
    private var mTextSize = DensityUtil.sp2px(context, 13f)
    private var mColorType = 1
    private var mContent: String = "999"
    private var mProgress = 0
    private val textPaint: Paint
    private val progressPaint: Paint

    private val textColorList = arrayOf(
        Color.parseColor("#359FFF"),
        Color.parseColor("#FF9521"),
        Color.parseColor("#9DA3B2")
    )
    private val progressColorList = arrayOf(
        Color.parseColor("#268CFF"),
        Color.parseColor("#FF9521"),
        Color.parseColor("#D3EBFF")
    )

    init {
        val a = context.obtainStyledAttributes(attrs, R.styleable.DailyPlanNumberProgress)
        textPadding = a.getDimensionPixelSize(R.styleable.DailyPlanNumberProgress_progress_textPadding, 8)
        mTextSize = a.getDimensionPixelSize(R.styleable.DailyPlanNumberProgress_progress_textSize, 13)
        mColorType = a.getInt(R.styleable.DailyPlanNumberProgress_color_type, 0)
        a.recycle()

        textPaint = Paint().apply {
            style = Paint.Style.FILL
            strokeJoin = Paint.Join.ROUND
            strokeCap = Paint.Cap.ROUND
            color = textColorList[mColorType]
        }

        progressPaint = Paint().apply {
            style = Paint.Style.FILL
            strokeJoin = Paint.Join.ROUND
            strokeCap = Paint.Cap.ROUND
            color = progressColorList[mColorType]
        }
        progressDrawable = null
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val width = width
        val height = height

        textPaint.textSize = mTextSize.toFloat()
        val testTextWidth = textPaint.measureText(mContent)
        val textBounds = Rect()
        textPaint.getTextBounds(mContent, 0, mContent.length, textBounds)
        val contentHeight = textBounds.height()

        val progressFullWidth: Float = width.toFloat() - testTextWidth - textPadding
        val progressHeight = height / 2
        val progressTop = height / 2 - progressHeight / 2
        val progressWidth = (mProgress * 1.0 / max) * progressFullWidth

        val rectF = RectF(
            0f, progressTop.toFloat(), progressWidth.toFloat(),
            (progressTop + progressHeight).toFloat()
        )
        val roundRadius = progressHeight / 2f
        canvas.drawRoundRect(rectF, roundRadius, roundRadius, progressPaint)
        val progressText = mContent

        canvas.drawText(
            progressText,
            progressWidth.toFloat() + textPadding,
            ((height - contentHeight) / 2 + contentHeight).toFloat(),
            textPaint
        )
    }

    fun setData(content: String, progress: Int) {
        this.mContent = content
        this.mProgress = progress
        requestLayout()
    }
}