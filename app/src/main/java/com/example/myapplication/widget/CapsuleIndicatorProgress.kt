package com.example.myapplication.widget

/**
 * @Author qipeng
 * @Date 2024/11/29
 * @Desc
 */
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.Shader
import android.util.AttributeSet
import android.widget.ProgressBar
import com.example.myapplication.R

class CapsuleIndicatorProgress @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = android.R.attr.progressBarStyleHorizontal
) : ProgressBar(context, attrs, defStyleAttr) {
    // 橙、黄、蓝、绿
    private val endColorList = arrayOf(
        Color.parseColor("#FB5200"),
        Color.parseColor("#FF8103"),
        Color.parseColor("#087AFF"),
        Color.parseColor("#0DC43D")
    )
    private val startColorList  = arrayOf(
        Color.parseColor("#FF9064"),
        Color.parseColor("#FFE06D"),
        Color.parseColor("#03D3FF"),
        Color.parseColor("#85FB13")
    )
    private val indicatorColorList = arrayOf(
        Color.parseColor("#FB5200"),
        Color.parseColor("#FF9521"),
        Color.parseColor("#268CFF"),
        Color.parseColor("#00C758")
    )
    private val textPadding = 10f
    private val indicatorBorderWidth = 8f
    private val indicatorPadding = 8f
    private val endSpaceLength = 8f
    private val mTextSize = 30f
    private var changeByProgress: Boolean = false
    private var progressColor: Int = Color.parseColor("#E7EBF2")
    private var gradientColorStart: Int = startColorList[3]
    private var gradientColorEnd: Int = endColorList[3]
    private var indicatorColor: Int = indicatorColorList[3]
    private val progressPaint: Paint
    private val gradientPaint: Paint
    private val indicatorPaint: Paint
    private val indicatorStrokePaint: Paint

    init {
        val a = context.obtainStyledAttributes(attrs, R.styleable.CapsuleIndicatorProgress)
        changeByProgress =
            a.getBoolean(R.styleable.CapsuleIndicatorProgress_changeByProgress, false)
        a.recycle()
        progressPaint = Paint().apply {
            color = progressColor
            style = Paint.Style.FILL
            strokeJoin = Paint.Join.ROUND
            strokeCap = Paint.Cap.ROUND
        }
        gradientPaint = Paint().apply {
            style = Paint.Style.FILL
            strokeJoin = Paint.Join.ROUND
            strokeCap = Paint.Cap.ROUND
        }
        indicatorPaint = Paint().apply {
            style = Paint.Style.FILL
            strokeJoin = Paint.Join.ROUND
            strokeCap = Paint.Cap.ROUND
        }
        indicatorStrokePaint = Paint().apply {
            color = Color.WHITE
            style = Paint.Style.FILL
            strokeJoin = Paint.Join.ROUND
            strokeCap = Paint.Cap.ROUND
        }
        progressDrawable = null
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val width = width
        val height = height
        val progressFullWidth = width - endSpaceLength
        val progressHeight = height / 2
        val progressTop = height / 2 - progressHeight / 2

        val progress = progress
        val max = max
        val progressWidth = (progress * 1.0 / max) * progressFullWidth
        if (changeByProgress) {
            gradientColorStart = startColorList[getIndexByProgress(progress)]
            gradientColorEnd = endColorList[getIndexByProgress(progress)]
            indicatorColor = indicatorColorList[getIndexByProgress(progress)]
        }
        // 绘制默认灰色进度
        val rectF = RectF(
            0f, progressTop.toFloat(), progressFullWidth,
            (progressTop + progressHeight).toFloat()
        )
        val roundRadius = progressHeight / 2f
        canvas.drawRoundRect(rectF, roundRadius, roundRadius, progressPaint)

        // 绘制渐变已到达进度
        val gradient = LinearGradient(
            0f,
            progressTop.toFloat(),
            progressWidth.toFloat(),
            (progressTop + progressHeight).toFloat(),
            gradientColorStart,
            gradientColorEnd,
            Shader.TileMode.CLAMP
        )
        gradientPaint.shader = gradient
        val gradientRectF = RectF(
            0f,
            progressTop.toFloat(),
            progressWidth.toFloat(),
            (progressTop + progressHeight).toFloat()
        )
        canvas.drawRoundRect(gradientRectF, roundRadius, roundRadius, gradientPaint)

        val textPaint = Paint().apply {
            color = Color.WHITE
            textSize = mTextSize
        }
        val progressText = "$progress%"
        val textWidth = textPaint.measureText(progressText)

        // 绘制胶囊样式指示器并显示进度文案
        val indicatorWidth = textWidth + textPadding
        val indicatorHeight = progressHeight * 1f + indicatorPadding
        val indicatorX:Float = if(progressWidth>indicatorWidth)(progressWidth - indicatorWidth).toFloat() else 0f
        val indicatorY = height / 2 - indicatorHeight / 2
        val indicatorRadius = indicatorHeight / 2
        val indicatorRight:Float = if (progressWidth > indicatorWidth) {
            progressWidth.toFloat()
        } else {
            indicatorWidth
        }
        val indicatorRectF = RectF(
            indicatorX, indicatorY,
            indicatorRight, indicatorY + indicatorHeight
        )
        indicatorPaint.color = indicatorColor

        val bgRectF = RectF(
            indicatorX - indicatorBorderWidth / 2,
            indicatorY - indicatorBorderWidth / 2,
            indicatorRight + indicatorBorderWidth / 2,
            indicatorY + indicatorHeight + indicatorBorderWidth / 2
        )
        canvas.drawRoundRect(
            bgRectF,
            indicatorRadius + indicatorBorderWidth / 2,
            indicatorRadius + indicatorBorderWidth / 2,
            indicatorStrokePaint
        )
        canvas.drawRoundRect(indicatorRectF, indicatorRadius, indicatorRadius, indicatorPaint)

        // 文字
        canvas.drawText(
            progressText,
            (indicatorX + (indicatorWidth - textWidth) / 2),
            indicatorY + indicatorHeight / 2 + textPadding,
            textPaint
        )
    }

    private fun getIndexByProgress(progress: Int): Int {
        return when (progress) {
            in 0..49 -> 0
            in 50..89 -> 1
            in 90..99 -> 2
            else -> 3
        }
    }
}