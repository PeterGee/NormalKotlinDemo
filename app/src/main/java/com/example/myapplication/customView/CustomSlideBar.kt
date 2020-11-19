package com.example.myapplication.customView

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.Shader
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.example.myapplication.R
import kotlin.math.max
import kotlin.math.min

/**
 * @author qipeng
 * @date 2019/10/31
 * @desc SlideBar
 */
class CustomSlideBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    /**
     * 进度条的宽度
     */
    private var lineWidth: Int = 0
    /**
     * 进度条的长度
     */
    private var lineLength = 400
    /**
     * 游标图片宽度
     */
    private var imageWidth: Int = 0
    /**
     * 游标图片高度
     */
    private var imageHeight: Int = 0
    /**
     * 进度条的颜色
     */
    private var inColor: Int = 0
    /**
     * 左边图标的图片
     */
    private var bitmapLow: Bitmap? = null
    /**
     * 右边图标图片
     */
    private var bitmapBig: Bitmap? = null
    /**
     * 左边图标所在X轴的位置
     */
    private var slideLowX: Int = 0
    /**
     * 右边图标所在X轴的位置
     */
    private var slideBigX: Int = 0
    /**
     * 游标高度
     */
    private var bitmapHeight: Int = 0
    /**
     * 游标宽度
     */
    private var bitmapWidth: Int = 0
    /**
     * padding
     */
    private val mPaddingLeft = 23
    private val mPaddingRight = 23
    private val mPaddingTop = 50
    private val mPaddingBottom = 10
    /**
     * 进度条开始的位置
     */
    private var lineStart = mPaddingLeft
    /**
     * 线的Y轴位置
     */
    private var lineY: Int = 0
    /**
     * 进度条的结束位置
     */
    private var lineEnd = lineLength + mPaddingLeft
    /**
     * 选择器的最大值
     */
    private var bigValue = 100
    /**
     * 选择器的最小值
     */
    private val smallValue = 0
    /**
     * 选择器的当前最小值
     */
    private var minProgress: Float = 0.toFloat()
    /**
     * 选择器的当前最大值
     */
    private var maxProgress: Float = 0.toFloat()
    private var linePaint: Paint? = null
    private var bitmapPaint: Paint? = null

    private var onProgressListener: IProgressListener? = null

    init {
        val typedArray =
            context.theme.obtainStyledAttributes(attrs,
                R.styleable.CustomSlideBar, defStyleAttr, 0)
        val size = typedArray.indexCount
        for (i in 0 until size) {
            when (val type = typedArray.getIndex(i)) {
                R.styleable.CustomSlideBar_inColor -> inColor =
                    typedArray.getColor(type, Color.BLACK)
                R.styleable.CustomSlideBar_lineHeight -> lineWidth =
                    typedArray.getDimension(type, dip2px(getContext(), 10f).toFloat()).toInt()
                R.styleable.CustomSlideBar_imageLow -> bitmapLow =
                    BitmapFactory.decodeResource(resources, typedArray.getResourceId(type, 0))
                R.styleable.CustomSlideBar_imageBig -> bitmapBig =
                    BitmapFactory.decodeResource(resources, typedArray.getResourceId(type, 0))
                R.styleable.CustomSlideBar_imageheight -> imageHeight =
                    typedArray.getDimension(type, dip2px(getContext(), 20f).toFloat()).toInt()
                R.styleable.CustomSlideBar_imagewidth -> imageWidth =
                    typedArray.getDimension(type, dip2px(getContext(), 20f).toFloat()).toInt()
                R.styleable.CustomSlideBar_bigValue -> bigValue = typedArray.getInteger(type, 0)
                else -> {
                }
            }
        }
        typedArray.recycle()
        init()
    }

    private fun init() {
        if (bitmapLow == null) {
            bitmapLow = BitmapFactory.decodeResource(resources,
                R.mipmap.ic_launcher
            )
        }
        if (bitmapBig == null) {
            bitmapBig = BitmapFactory.decodeResource(resources,
                R.mipmap.ic_launcher
            )
        }
        // 获取游标实际大小
        bitmapHeight = bitmapLow!!.height
        bitmapWidth = bitmapLow!!.width
        // 设置想要的大小
        val newWidth = imageWidth
        val newHeight = imageHeight
        // 计算缩放比例
        val scaleWidth = newWidth.toFloat() / bitmapWidth
        val scaleHeight = newHeight.toFloat() / bitmapHeight
        val matrix = Matrix()
        matrix.postScale(scaleWidth, scaleHeight)
        // 进行缩放
        bitmapLow = Bitmap.createBitmap(bitmapLow!!, 0, 0, bitmapWidth, bitmapHeight, matrix, true)
        bitmapBig = Bitmap.createBitmap(bitmapBig!!, 0, 0, bitmapWidth, bitmapHeight, matrix, true)
        // 重新获取宽度和高度
        bitmapHeight = bitmapLow!!.height
        bitmapWidth = bitmapLow!!.width
        // init vernier
        slideLowX = lineStart
        slideBigX = lineStart + bitmapWidth
        minProgress = smallValue.toFloat()
        maxProgress = bigValue.toFloat()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = getMyMeasureWidth(widthMeasureSpec)
        val height = getMyMeasureHeight(heightMeasureSpec)
        setMeasuredDimension(width, height)
    }

    private fun getMyMeasureHeight(heightMeasureSpec: Int): Int {
        val mode = MeasureSpec.getMode(heightMeasureSpec)
        var size = MeasureSpec.getSize(heightMeasureSpec)
        size = if (mode == MeasureSpec.EXACTLY) {
            // Exactly
            max(size, mPaddingBottom + mPaddingTop + bitmapHeight + 10)
        } else {
            //wrap content
            val height = mPaddingBottom + mPaddingTop + bitmapHeight + 10
            min(size, height)
        }
        return size
    }

    private fun getMyMeasureWidth(widthMeasureSpec: Int): Int {
        val mode = MeasureSpec.getMode(widthMeasureSpec)
        var size = MeasureSpec.getSize(widthMeasureSpec)
        size = if (mode == MeasureSpec.EXACTLY) {
            max(size, mPaddingLeft + mPaddingRight + bitmapWidth * 2)
        } else {
            //wrap content
            val width = mPaddingLeft + mPaddingRight + bitmapWidth * 2
            min(size, width)
        }
        lineLength = size - mPaddingLeft - mPaddingRight - bitmapWidth
        lineEnd = lineLength + mPaddingLeft + bitmapWidth / 2
        lineStart = mPaddingLeft + bitmapWidth / 2
        slideBigX = lineStart + bitmapWidth
        slideLowX = lineStart
        return size
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        lineY = height - mPaddingBottom - bitmapHeight / 2
        if (linePaint == null) {
            linePaint = Paint()
        }
        //progress normal
        linePaint!!.isAntiAlias = true
        linePaint!!.strokeWidth = lineWidth.toFloat()
        linePaint!!.color = inColor
        linePaint!!.strokeCap = Paint.Cap.ROUND
        canvas.drawLine(
            slideLowX.toFloat(),
            lineY.toFloat(),
            slideBigX.toFloat(),
            lineY.toFloat(),
            linePaint!!
        )
        // gradient color
        val colors = intArrayOf(
            Color.parseColor("#D9172A"),
            Color.parseColor("#9C38FF"),
            Color.parseColor("#3280EA")
        )
        val shader = LinearGradient(
            lineStart.toFloat(),
            lineY.toFloat(),
            slideBigX.toFloat(),
            lineY.toFloat(),
            colors,
            null,
            Shader.TileMode.CLAMP
        )
        linePaint!!.shader = shader
        linePaint!!.strokeCap = Paint.Cap.ROUND
        //outerLine
        canvas.drawLine(
            lineStart.toFloat(),
            lineY.toFloat(),
            slideLowX.toFloat(),
            lineY.toFloat(),
            linePaint!!
        )
        canvas.drawLine(
            slideBigX.toFloat(),
            lineY.toFloat(),
            lineEnd.toFloat(),
            lineY.toFloat(),
            linePaint!!
        )
        //draw vernier
        if (bitmapPaint == null) {
            bitmapPaint = Paint()
        }
        canvas.drawBitmap(
            bitmapLow!!,
            (slideLowX - bitmapWidth / 2).toFloat(),
            (lineY - bitmapHeight / 2).toFloat(),
            bitmapPaint
        )
        canvas.drawBitmap(
            bitmapBig!!,
            (slideBigX - bitmapWidth / 2).toFloat(),
            (lineY - bitmapHeight / 2).toFloat(),
            bitmapPaint
        )
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        super.onTouchEvent(event)
        val nowX = event.x
        if (event.action == MotionEvent.ACTION_MOVE) {//当前 X坐标在线上 且在左边游标的右边
            if (nowX >= slideLowX + bitmapWidth && nowX <= lineEnd + bitmapWidth / 2) {
                slideBigX = nowX.toInt()
                if (slideBigX > lineEnd) {
                    slideBigX = lineEnd
                }
                updateRange()
                postInvalidate()
            }
        }
        return true
    }

    private fun updateRange() {
        // 左边游标数值
        minProgress = getRange(slideLowX.toFloat())
        // 右边游标数值
        maxProgress = getRange(slideBigX.toFloat())
        if (onProgressListener != null) {
            onProgressListener!!.onRange(minProgress, maxProgress)
        }
    }

    /**
     * 获取当前值
     */
    private fun getRange(range: Float): Float {
        return (range - lineStart) * (bigValue - smallValue) / lineLength + smallValue
    }

    private fun dip2px(context: Context, dpValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }


    interface IProgressListener {
        fun onRange(low: Float, big: Float)
    }

    fun setOnProgressListener(onProgressListener: IProgressListener) {
        this.onProgressListener = onProgressListener
    }
}

