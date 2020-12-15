package com.example.myapplication.touchablePop

import android.content.Context
import android.graphics.drawable.BitmapDrawable
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.WindowManager
import android.widget.ImageView
import android.widget.PopupWindow
import android.widget.Toast
import com.example.myapplication.R
import kotlin.math.abs

/**
 * @date 2020/12/15
 * @author qipeng
 * @desc
 */
class TouchablePopUpWindow @JvmOverloads constructor(
    context: Context
) : PopupWindow(context) {

    private var mContext = context
    private var mStartY: Int = 0
    private var mDiffY: Int = 0

    init {
        width = WindowManager.LayoutParams.MATCH_PARENT
        height = WindowManager.LayoutParams.MATCH_PARENT
        // 设置bitmapDrawable 解决存在边缘问题
        setBackgroundDrawable(BitmapDrawable())
        val mContentView = LayoutInflater.from(context).inflate(R.layout.touchable_pop, null)
        val mIvTop = mContentView.findViewById<ImageView>(R.id.iv_top)
        animationStyle = R.style.PopupWindowAnimation
        contentView = mContentView
        // 全屏
        isClippingEnabled = false

        mIvTop.setOnTouchListener { _, event ->
            event.run {
                when (action) {
                    MotionEvent.ACTION_DOWN -> {
                        mStartY = rawY.toInt()
                    }
                    MotionEvent.ACTION_MOVE -> {
                        mDiffY = rawY.toInt() - mStartY
                        // 越界处理
                        if (mDiffY > 0) mDiffY = 0
                        update(0, -mDiffY, -1, -1, true)
                    }
                    MotionEvent.ACTION_UP -> {
                        // click 事件处理
                        if (abs(rawY.toInt() - mStartY) < 10) {
                            Toast.makeText(mContext, "clicked", Toast.LENGTH_SHORT).show()
                        } else {
                            dismiss()
                        }

                    }
                }
            }
            true
        }
    }

}