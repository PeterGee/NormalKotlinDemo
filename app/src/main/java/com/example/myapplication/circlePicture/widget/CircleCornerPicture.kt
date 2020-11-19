package com.example.myapplication.circlePicture.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Path
import android.util.AttributeSet
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import com.example.myapplication.R
import kotlin.math.max


/**
 * @date 2019/12/10
 * @author qipeng
 * @desc 圆角图片
 */
class CircleCornerPicture @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet,
    defStyleAttr: Int = 0
) :
   AppCompatImageView(context, attrs, defStyleAttr) {
    var width = 0f
    var height = 0f

    private val defaultRadius = 0
    private var radius = 0
    private var leftTopRadius = 0
    private var rightTopRadius = 0
    private var rightBottomRadius = 0
    private var leftBottomRadius = 0

    init {
        init(context, attrs)
    }


    private fun init(
        context: Context,
        attrs: AttributeSet
    ) {

        val array =
            context.obtainStyledAttributes(attrs, R.styleable.CircleCornerPicture)
        radius =
            array.getDimensionPixelOffset(R.styleable.CircleCornerPicture_picture_radius, defaultRadius)
        leftTopRadius = array.getDimensionPixelOffset(
            R.styleable.CircleCornerPicture_left_top_radius,
            defaultRadius
        )
        rightTopRadius = array.getDimensionPixelOffset(
            R.styleable.CircleCornerPicture_right_top_radius,
            defaultRadius
        )
        rightBottomRadius = array.getDimensionPixelOffset(
            R.styleable.CircleCornerPicture_right_bottom_radius,
            defaultRadius
        )
        leftBottomRadius = array.getDimensionPixelOffset(
            R.styleable.CircleCornerPicture_left_bottom_radius,
            defaultRadius
        )
        //如果四个角的值没有设置，那么就使用通用的radius的值。
        if (defaultRadius == leftTopRadius) {
            leftTopRadius = radius
        }
        if (defaultRadius == rightTopRadius) {
            rightTopRadius = radius
        }
        if (defaultRadius == rightBottomRadius) {
            rightBottomRadius = radius
        }
        if (defaultRadius == leftBottomRadius) {
            leftBottomRadius = radius
        }
        array.recycle()
    }

    override fun onLayout(
        changed: Boolean,
        left: Int,
        top: Int,
        right: Int,
        bottom: Int
    ) {
        super.onLayout(changed, left, top, right, bottom)
        width = getWidth().toFloat()
        height = getHeight().toFloat()
    }

    /**
     * 设置角度
     */
    fun setCorners(
        leftTopCorner: Int,
        rightTopCorner: Int,
        leftBottomCorner: Int,
        rightBottomCorner: Int
    ) {
        Log.d("peter", "setCorners")
        leftTopRadius = leftTopCorner
        rightTopRadius = rightTopCorner
        leftBottomRadius = leftBottomCorner
        rightBottomRadius = rightBottomCorner
        invalidate()
    }


    override fun onDraw(canvas: Canvas) {
        val maxLeft = max(leftTopRadius, leftBottomRadius)
        val maxRight = max(rightTopRadius, rightBottomRadius)
        val minWidth = maxLeft + maxRight
        val maxTop = max(leftTopRadius, rightTopRadius)
        val maxBottom = max(leftBottomRadius, rightBottomRadius)
        val minHeight = maxTop + maxBottom
        if (width >= minWidth && height > minHeight) {
            Log.d("peter", "onDraw")
            val path = Path().apply {
                //四个角：右上，右下，左下，左上
                moveTo(leftTopRadius.toFloat(), 0f)
                lineTo(width - rightTopRadius, 0f)
                quadTo(width, 0f, width, rightTopRadius.toFloat())
                lineTo(width, height - rightBottomRadius)
                quadTo(width, height, width - rightBottomRadius, height)
                lineTo(leftBottomRadius.toFloat(), height)
                quadTo(0f, height, 0f, height - leftBottomRadius)
                lineTo(0f, leftTopRadius.toFloat())
                quadTo(0f, 0f, leftTopRadius.toFloat(), 0f)
            }
            canvas.clipPath(path)
        }
        super.onDraw(canvas)
    }

}
