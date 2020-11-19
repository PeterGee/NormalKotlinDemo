package com.example.myapplication.customView

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.widget.TextView

/**
 * @date 2020/1/2
 * @author qipeng
 * @desc
 */
class DrawableLeftCenterTextView @JvmOverloads constructor(context: Context, attributeSet: AttributeSet
                                                           , defaultStyle: Int = 0) : TextView(context, attributeSet, defaultStyle) {
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val drawables = compoundDrawables
        drawables.let {
            val drawableLeft = drawables[0]
            drawableLeft?.let {
                val mTextWidth = paint.measureText(text.toString())
                val bodyWidth = it.intrinsicWidth + mTextWidth + compoundDrawablePadding
                canvas?.translate((width - bodyWidth) / 2, 0f)
            }
        }
    }
}