package com.example.myapplication.customView

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.BounceInterpolator
import androidx.constraintlayout.widget.ConstraintLayout
import kotlin.math.min
import kotlin.math.sqrt


/**
 * @Author qipeng
 * @Date 2023/12/11
 * @Desc
 */
class CustomDragWidget @JvmOverloads constructor(
    mContext: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(mContext, attrs, defStyleAttr) {

    private var mIsDrug = true
    private var mLastRawX = 0f
    private var mLastRawY = 0f
    private var mRootMeasuredWidth = 0
    private var mRootMeasuredHeight = 0
    private var mRootTopY = 0
    private var clickedChild: View? = null
    private var mListener:IChildClickListener?=null


    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        super.dispatchTouchEvent(event)
        return true
    }

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        return true
    }

    override fun onTouchEvent(ev: MotionEvent): Boolean {
            val mRawX = ev.rawX
            val mRawY = ev.rawY
            when (ev.action) {
                MotionEvent.ACTION_DOWN -> {
                    mIsDrug = false
                    mLastRawX = mRawX
                    mLastRawY = mRawY
                    clickedChild = findChildViewUnder(ev.rawX.toInt(), ev.rawY.toInt());
                    val mViewGroup = parent as ViewGroup
                    val location = IntArray(2)
                    mViewGroup.getLocationInWindow(location)
                    mRootMeasuredHeight = mViewGroup.measuredHeight
                    mRootMeasuredWidth = mViewGroup.measuredWidth
                    mRootTopY = location[1]
                }

                MotionEvent.ACTION_MOVE -> {
                    if (mRawX >= 0 && mRawX <= mRootMeasuredWidth && mRawY >= mRootTopY && mRawY <= mRootMeasuredHeight + mRootTopY) {
                        val differenceValueX = mRawX - mLastRawX
                        val differenceValueY = mRawY - mLastRawY
                        if (!mIsDrug) {
                            mIsDrug = sqrt((differenceValueX * differenceValueX + differenceValueY * differenceValueY).toDouble()) >= 2
                        }
                        val ownX = x
                        val ownY = y
                        var endX = ownX + differenceValueX
                        var endY = ownY + differenceValueY
                        val maxX = (mRootMeasuredWidth - width).toFloat()
                        val maxY = (mRootMeasuredHeight - height).toFloat()
                        endX = if (endX < 0) 0F else min(endX, maxX)
                        endY = if (endY < 0) 0F else min(endY, maxY)
                        x = endX
                        y = endY
                        mLastRawX = mRawX
                        mLastRawY = mRawY
                    }
                }
                MotionEvent.ACTION_UP ->{
                    if (mIsDrug) {
                        val center = (mRootMeasuredWidth shr 1).toFloat()
                        mLastRawX = if (mLastRawX <= center) 0f else (mRootMeasuredWidth - width).toFloat()
                        animate()
                            .setInterpolator(BounceInterpolator())
                            .setDuration(500)
                            .x(mLastRawX)
                            .start()
                    }else{
                        clickedChild?.let { mListener?.onChildClicked(it) }
                    }
                    clickedChild = null
                }

            }
        return if (mIsDrug) mIsDrug else super.onTouchEvent(ev)
    }

    private fun findChildViewUnder(x: Int, y: Int): View? {
        for (i in childCount-1 downTo 0) {
            val child = getChildAt(i)
            val location = IntArray(2)
            child.getLocationOnScreen(location)
            if (x >= location[0] && x <= location[0] + child.width && y >= location[1] && y <= location[1] + child.height) {
                return child
            }
        }
        return null
    }

    interface IChildClickListener{
        fun onChildClicked(child: View)
    }

    fun setChildClickListener(listener:IChildClickListener){
        this.mListener = listener
    }

}

