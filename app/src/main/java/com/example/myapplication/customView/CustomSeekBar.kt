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

/**
 * @author qipeng
 * @date 2019/11/11
 * @desc
 */
class CustomSeekBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    /**
     * 进度条宽度
     */
    private var lineWidth: Int = 0
    /**
     * 进度条长度
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
     * 左边的游标是否在动
     */
    private var isLowerMoving: Boolean = false
    /**
     * 右边的游标是否在动
     */
    private var isUpperMoving: Boolean = false
    /**
     * 两个游标外部 进度条颜色
     */
    private var outColor = Color.BLUE
    /**
     * 左边图标图片
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
     * 线进度条开始的位置
     */
    private var lineStart = mPaddingLeft
    /**
     * 线的Y轴位置
     */
    private var lineY: Int = 0
    /**
     * 进度条结束位置
     */
    private var lineEnd = lineLength + mPaddingLeft
    /**
     * 选择器的最大值
     */
    private var bigValue = 100
    /**
     * 选择器的最小值
     */
    private var smallValue = 0
    /**
     * 选择器的当前最小值
     */
    private var smallRange: Float = 0.toFloat()
    /**
     * 选择器的当前最大值
     */
    private var bigRange: Float = 0.toFloat()
    private var linePaint: Paint? = null
    private var bitmapPaint: Paint? = null

    private var mListener: IRangeChangeListener? = null

    init {
        val typedArray = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.DoubleSlideSeekBar,
            defStyleAttr,
            0
        )
        val size = typedArray.indexCount
        for (i in 0 until size) {
            val type = typedArray.getIndex(i)
            when (type) {
                R.styleable.DoubleSlideSeekBar_lineHeight -> lineWidth =
                    typedArray.getDimension(type, dip2px(getContext(), 10f).toFloat()).toInt()
                R.styleable.DoubleSlideSeekBar_outColor -> outColor =
                    typedArray.getColor(type, Color.YELLOW)
                R.styleable.DoubleSlideSeekBar_imageLow -> bitmapLow =
                    BitmapFactory.decodeResource(resources, typedArray.getResourceId(type, 0))
                R.styleable.DoubleSlideSeekBar_imageBig -> bitmapBig =
                    BitmapFactory.decodeResource(resources, typedArray.getResourceId(type, 0))
                R.styleable.DoubleSlideSeekBar_imageheight -> imageHeight =
                    typedArray.getDimension(type, dip2px(getContext(), 20f).toFloat()).toInt()
                R.styleable.DoubleSlideSeekBar_imagewidth -> imageWidth =
                    typedArray.getDimension(type, dip2px(getContext(), 20f).toFloat()).toInt()
                R.styleable.DoubleSlideSeekBar_bigValue -> bigValue =
                    typedArray.getInteger(type, 100)
                R.styleable.DoubleSlideSeekBar_smallValue -> smallValue =
                    typedArray.getInteger(type, 100)
                else -> {
                }
            }
        }
        typedArray.recycle()
        init()
    }

    private fun init() {
        /**游标的默认图 */
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
        /**游标图片的真实高度 之后通过缩放比例可以把图片设置成想要的大小 */
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
        /**缩放图片 */
        bitmapLow = Bitmap.createBitmap(bitmapLow!!, 0, 0, bitmapWidth, bitmapHeight, matrix, true)
        bitmapBig = Bitmap.createBitmap(bitmapBig!!, 0, 0, bitmapWidth, bitmapHeight, matrix, true)
        /**重新获取游标图片的宽高 */
        bitmapHeight = bitmapLow!!.height
        bitmapWidth = bitmapLow!!.width
        /**初始化两个游标的位置 */
        slideLowX = lineStart
        slideBigX = lineStart + bitmapWidth
        smallRange = smallValue.toFloat()
        bigRange = bigValue.toFloat()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = getMyMeasureWidth(widthMeasureSpec)
        val height = getMyMeasureHeight(heightMeasureSpec)
        setMeasuredDimension(width, height)
    }

    private fun getMyMeasureHeight(heightMeasureSpec: Int): Int {
        val mode = View.MeasureSpec.getMode(heightMeasureSpec)
        var size = View.MeasureSpec.getSize(heightMeasureSpec)
        if (mode == View.MeasureSpec.EXACTLY) {
            size = Math.max(size, mPaddingBottom + mPaddingTop + bitmapHeight + 10)
        } else {
            //wrap content
            val height = mPaddingBottom + mPaddingTop + bitmapHeight + 10
            size = Math.min(size, height)
        }
        return size
    }

    private fun getMyMeasureWidth(widthMeasureSpec: Int): Int {
        val mode = View.MeasureSpec.getMode(widthMeasureSpec)
        var size = View.MeasureSpec.getSize(widthMeasureSpec)
        if (mode == View.MeasureSpec.EXACTLY) {
            size = Math.max(size, mPaddingLeft + mPaddingRight + bitmapWidth * 2)
        } else {
            //wrap content
            val width = mPaddingLeft + mPaddingRight + bitmapWidth * 2
            size = Math.min(size, width)
        }

        lineLength = size - mPaddingLeft - mPaddingRight - bitmapWidth
        lineEnd = lineLength + mPaddingLeft + bitmapWidth / 2
        lineStart = mPaddingLeft + bitmapWidth / 2
        //初始化 游标位置
        slideBigX = lineStart + bitmapWidth
        slideLowX = lineStart
        return size
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        // Y轴 坐标
        lineY = height - mPaddingBottom - bitmapHeight / 2
        if (linePaint == null) {
            linePaint = Paint()
        }
        //画内部线
        linePaint!!.isAntiAlias = true
        linePaint!!.strokeWidth = lineWidth.toFloat()
        linePaint!!.strokeCap = Paint.Cap.ROUND
        val colors = intArrayOf(
            Color.parseColor("#D9172A"),
            Color.parseColor("#9C38FF"),
            Color.parseColor("#3280EA")
        )
        val shader = LinearGradient(
            slideLowX.toFloat(), lineY.toFloat(),
            slideBigX.toFloat(), lineY.toFloat(), colors, null,
            Shader.TileMode.CLAMP
        )
        linePaint!!.shader = shader
        canvas.drawLine(
            slideLowX.toFloat(),
            lineY.toFloat(),
            slideBigX.toFloat(),
            lineY.toFloat(),
            linePaint!!
        )
        linePaint!!.shader = null
        linePaint!!.color = outColor
        linePaint!!.strokeCap = Paint.Cap.ROUND
        //画 外部线
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
        //画游标
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
        //事件机制
        super.onTouchEvent(event)
        val nowX = event.x
        val nowY = event.y
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                //按下 在进度条范围上
                val rightY = Math.abs(nowY - lineY) < bitmapHeight / 2
                //按下 在左边游标上
                val lowSlide = Math.abs(nowX - slideLowX) < bitmapWidth / 2
                //按下 在右边游标上
                val bigSlide = Math.abs(nowX - slideBigX) < bitmapWidth / 2
                if (rightY && lowSlide) {
                    isLowerMoving = true
                } else if (rightY && bigSlide) {
                    isUpperMoving = true
                    //点击了游标外部 的线上
                } else if (nowX >= lineStart && nowX <= slideLowX - bitmapWidth / 2 && rightY) {
                    slideLowX = nowX.toInt()
                    updateRange()
                    postInvalidate()
                } else if (nowX <= lineEnd && nowX >= slideBigX + bitmapWidth / 2 && rightY) {
                    slideBigX = nowX.toInt()
                    updateRange()
                    postInvalidate()
                }
            }
            MotionEvent.ACTION_MOVE ->
                //左边游标是运动状态
                if (isLowerMoving) {
                    //当前 X坐标在线上 且在右边游标的左边
                    if (nowX <= slideBigX - bitmapWidth && nowX >= lineStart - bitmapWidth / 2) {
                        slideLowX = nowX.toInt()
                        if (slideLowX < lineStart) {
                            slideLowX = lineStart
                        }
                        //更新进度
                        updateRange()
                        postInvalidate()
                    }
                } else if (isUpperMoving) {
                    //当前 X坐标在线上 且在左边游标的右边
                    if (nowX >= slideLowX + bitmapWidth && nowX <= lineEnd + bitmapWidth / 2) {
                        slideBigX = nowX.toInt()
                        if (slideBigX > lineEnd) {
                            slideBigX = lineEnd
                        }
                        //更新进度
                        updateRange()
                        postInvalidate()

                    }
                }
            //手指抬起
            MotionEvent.ACTION_UP -> {
                isUpperMoving = false
                isLowerMoving = false
            }
            else -> {
            }
        }

        return true
    }

    private fun updateRange() {
        //当前 左边游标数值
        smallRange = getRange(slideLowX.toFloat())
        //当前 右边游标数值
        bigRange = getRange(slideBigX.toFloat())
        //接口 实现值的传递
        if (mListener != null) {
            mListener!!.onRange(smallRange, bigRange)
        }
    }

    /**
     * 获取当前值
     */
    private fun getRange(range: Float): Float {
        return (range - lineStart) * (bigValue - smallValue) / lineLength + smallValue
    }

    fun dip2px(context: Context, dpValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    /**
     * 写个接口 用来传递最大最小值
     */
    interface IRangeChangeListener {
        fun onRange(low: Float, high: Float)
    }

    fun setOnRangeListener(IRangeChangeListener: IRangeChangeListener) {
        this.mListener = IRangeChangeListener
    }

}
