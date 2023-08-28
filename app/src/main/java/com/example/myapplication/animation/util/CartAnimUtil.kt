package com.example.myapplication.animation.util

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.PointF
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.myapplication.R
import com.example.myapplication.animation.CartEvaluator

/**
 * @Author qipeng
 * @Date 2023/8/24
 * @Desc
 */
object CartAnimUtil {
    // 执行动画
    fun playAnim(context: Context, view: View, cartView: ImageView, parentView: ViewGroup) {


        //创建int数组，用来接收贝塞尔起始点坐标和终点坐标值
        val startPosition = IntArray(2)
        val endPosition = IntArray(2)
        view.getLocationInWindow(startPosition)
        cartView.getLocationInWindow(endPosition)
        val startF = PointF() //起始点 startF
        val endF = PointF() //终点 endF
        val controlF = PointF() //控制点 controlF
        startF.x = startPosition[0].toFloat()
        startF.y = startPosition[1].toFloat()
        endF.x = (((endPosition[0] + (cartView.width / 2)) - (view.width / 2))).toFloat() //微调处理，确保动画执行完毕“添加”图标中心点与购物车中心点重合
        endF.y = (endPosition[1] + (cartView.height.div(2) ?: 0) - view.height / 2).toFloat()
        controlF.x = endF.y
        controlF.y = startF.x

        // 创建执行动画的“添加”图标
        val imageView = ImageView(context)
        parentView.addView(imageView)
        imageView.setImageResource(R.drawable.add_cart)
        imageView.layoutParams.width = view.measuredWidth
        imageView.layoutParams.height = view.measuredHeight
        val animatorSet = AnimatorSet()
        val alphaAnimator = ObjectAnimator.ofFloat(imageView, "alpha", 1f, 0f)
        val valueAnimator = ValueAnimator.ofObject(CartEvaluator(controlF), startF, endF)
        animatorSet.play(valueAnimator).with(alphaAnimator)
        animatorSet.duration = 600
        animatorSet.start()

        valueAnimator.addUpdateListener { animation ->
            val pointF = animation.animatedValue as PointF
            imageView.x = pointF.x
            imageView.y = pointF.y
        }
        valueAnimator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                super.onAnimationEnd(animation)
                // 动画执行完毕，将执行动画的“添加”图标移除掉
                parentView.removeView(imageView)

                // 执行购物车缩放动画
                val animatorSet = AnimatorSet()
                val animatorX = ObjectAnimator.ofFloat(cartView, "scaleX", 1f, 1.2f, 1f)
                val animatorY = ObjectAnimator.ofFloat(cartView, "scaleY", 1f, 1.2f, 1f)
                animatorSet.play(animatorX).with(animatorY)
                animatorSet.duration = 400
                animatorSet.start()
            }
        })
        valueAnimator.duration = 800
        valueAnimator.start()
    }
}