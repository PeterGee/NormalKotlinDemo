package com.example.myapplication.customView

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

/**
 * @date 2019/10/30
 * @author qipeng
 * @desc 贝塞尔曲线
 */
class BezierView @JvmOverloads constructor(
    context: Context,
    attributes: AttributeSet,
    defaultStyle: Int = 0
) :
    View(context, attributes, defaultStyle) {

    private var mContext: Context? = null
    private lateinit var mPaint: Paint
    // 起点坐标
    private var startX: Float = 0f
    private var startY: Float = 0f
    // 中间点
    private var centerX: Float = 0f
    private var centerY: Float = 0f
    // 终点坐标
    private var endX: Float = 0f
    private var endY: Float = 0f
    // 事件点
    private var eventX: Float = 0f
    private var eventY: Float = 0f

    // 事件点2
    private var eventTwoX: Float = 0f
    private var eventTwoY: Float = 0f

    // 是否移动左边点
    private var isMoveEventOne = true

    init {
        init()
    }


    override fun onFinishInflate() {
        super.onFinishInflate()
        initView(context)
    }

    private fun initView(context: Context) {
        this.mContext = context
    }

    private fun init() {
        mPaint = Paint()
        mPaint.isAntiAlias = true
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        centerX = w / 2f
        centerY = h / 2f

        startX = centerX - 200
        startY = centerY

        endX = centerX + 200
        endY = centerY

        eventX = startX + 100
        eventY = centerY + 200

        eventTwoX = endX - 100
        eventTwoY = centerY + 200
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        mPaint.color = Color.BLUE

        canvas?.run {
            // 画个点
            canvas.drawCircle(startX, startY, 8f, mPaint)
            canvas.drawCircle(endX, endY, 8f, mPaint)
            canvas.drawCircle(eventX, eventY, 8f, mPaint)
            canvas.drawCircle(eventTwoX, eventTwoY, 8f, mPaint)


            // 绘制连线
            mPaint.strokeWidth = 3f
            canvas.drawLine(startX, startY, eventX, eventY, mPaint)
            canvas.drawLine(eventX, eventY, eventTwoX, eventTwoY, mPaint)
            canvas.drawLine(eventTwoX, eventTwoY, endX, endY, mPaint)


            // 绘制二阶贝塞尔曲线
            mPaint.color = Color.RED
            mPaint.style = Paint.Style.STROKE
            val path = Path()
            path.moveTo(startX, startY)
            // path.quadTo(eventX, eventY, endX, endY)
            path.cubicTo(eventX, eventY, eventTwoX, eventTwoY, endX, endY)
            canvas.drawPath(path, mPaint)
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> {
                if (isMoveEventOne) {
                    eventX = event.x
                    eventY = event.y
                } else {
                    eventTwoX = event.x
                    eventTwoY = event.y
                }
                invalidate()
            }

        }
        return true
    }

    fun moveEventOne() {
        isMoveEventOne = true
    }

    fun moveEventTwo() {
        isMoveEventOne = false
    }


}