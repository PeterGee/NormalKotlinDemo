package com.example.myapplication.animation

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.graphics.PointF
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.FragmentActivity
import com.example.myapplication.R


/**
 * @Author qipeng
 * @Date 2023/8/19
 * @Desc
 */
class CartActivity : FragmentActivity() {

    private var mAddOne: ImageView? = null
    private var mAddTwo: ImageView? = null
    private var mAddThree: ImageView? = null
    private var mCart: ImageView? = null
    private var mRootView: ViewGroup? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)
        initView()
        initListener()
    }

    // 初始化控件
    private fun initView() {
        mRootView = window.decorView as ViewGroup
        mCart = findViewById(R.id.mCart)
        mAddOne = findViewById(R.id.mAddOne)
        mAddTwo = findViewById(R.id.mAddTwo)
        mAddThree = findViewById(R.id.mAddThree)
    }

    // 初始化监听
    private fun initListener() {
        mAddOne?.setOnClickListener { view -> playAnim(view) }
        mAddTwo?.setOnClickListener { view -> playAnim(view) }
        mAddThree?.setOnClickListener { view -> playAnim(view) }
    }

    // 执行动画
    private fun playAnim(view: View) {

        //创建int数组，用来接收贝塞尔起始点坐标和终点坐标值
        val startPosition = IntArray(2)
        val endPosition = IntArray(2)
        view.getLocationInWindow(startPosition)
        mCart?.getLocationInWindow(endPosition)
        val startF = PointF() //起始点 startF
        val endF = PointF() //终点 endF
        val controlF = PointF() //控制点 controlF
        startF.x = startPosition[0].toFloat()
        startF.y = startPosition[1].toFloat()
        endF.x = (((endPosition[0] + (mCart!!.width / 2)) - (view.width / 2))).toFloat() //微调处理，确保动画执行完毕“添加”图标中心点与购物车中心点重合
        endF.y = (endPosition[1] + (mCart?.height?.div(2) ?: 0) - view.height / 2).toFloat()
        controlF.x = endF.x
        controlF.y = startF.y

        // 创建执行动画的“添加”图标
        val imageView = ImageView(this)
        mRootView!!.addView(imageView)
        imageView.setImageResource(R.drawable.add_cart)
        imageView.layoutParams.width = view.measuredWidth
        imageView.layoutParams.height = view.measuredHeight
        val valueAnimator = ValueAnimator.ofObject(CartEvaluator(controlF), startF, endF)
        valueAnimator.addUpdateListener { animation ->
            val pointF = animation.animatedValue as PointF
            imageView.x = pointF.x
            imageView.y = pointF.y
        }
        valueAnimator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                super.onAnimationEnd(animation)
                // 动画执行完毕，将执行动画的“添加”图标移除掉
                mRootView!!.removeView(imageView)

                // 执行购物车缩放动画
                val animatorSet = AnimatorSet()
                val animatorX = ObjectAnimator.ofFloat(mCart, "scaleX", 1f, 1.2f, 1f)
                val animatorY = ObjectAnimator.ofFloat(mCart, "scaleY", 1f, 1.2f, 1f)
                animatorSet.play(animatorX).with(animatorY)
                animatorSet.duration = 400
                animatorSet.start()
            }
        })
        valueAnimator.duration = 800
        valueAnimator.start()
    }
}