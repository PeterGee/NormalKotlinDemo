package com.example.myapplication.surfaceView

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView

/**
 *@date 2020/11/23
 *@author geqipeng
 *@desc
 */
class CustomSurfaceView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet,
    defaultStyle: Int? = 0
) : SurfaceView(context, attributeSet), SurfaceHolder.Callback {

    private var mSurfaceHolder: SurfaceHolder? = null
    private var mCanvas: Canvas? = null
    private var mPaint: Paint? = null

    init {
        mSurfaceHolder = holder
        mSurfaceHolder?.addCallback(this)
        isFocusable = true
        isFocusableInTouchMode = true
        keepScreenOn = true
        mPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.BLUE
            strokeWidth = 5f
            style = Paint.Style.STROKE
        }
    }


    override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {
        Log.d(TAG, "surfaceChanged")
    }

    override fun surfaceDestroyed(holder: SurfaceHolder?) {
        Log.d(TAG, "surfaceDestroyed")
    }

    override fun surfaceCreated(holder: SurfaceHolder?) {
        Log.d(TAG, "surfaceCreated")
        Thread(Runnable { drawView() }).start()
    }

    private fun drawView() {
        mCanvas = mSurfaceHolder?.lockCanvas()
        try {
            mCanvas?.apply {
                drawCircle(500f, 500f, 300f, mPaint)
                drawCircle(100f, 100f, 20f, mPaint)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            mCanvas?.let {
                mSurfaceHolder?.unlockCanvasAndPost(it)
            }
        }

    }

    companion object {
        const val TAG = "CustomSurfaceView"
    }
}