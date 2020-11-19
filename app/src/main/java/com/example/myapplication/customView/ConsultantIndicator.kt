package com.example.myapplication.customView

import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.viewpager.widget.ViewPager
import com.example.myapplication.R
import java.math.BigDecimal


/**
 * @date 2019/10/11
 * @author qipeng
 * @desc indicator
 */
class ConsultantIndicator @JvmOverloads constructor(
    context: Context,
    attr: AttributeSet? = null,
    defaultStyle: Int = 0
) : LinearLayout(context, attr, defaultStyle) {

    private var listSize: Int = 0
    private var moveLength: Int = 0
    /**
     * 指示器宽度
     */
    private var indicatorWidth: Int = 0
    private var indicatorParentWidth: Int = 0
    private var indicatorHeight: Int = 0
    private lateinit var tvInnerIndicator: ImageView
    private lateinit var indicatorParent: LinearLayout

    init {
        val ta =
            context.obtainStyledAttributes(attr,
                R.styleable.ConsultantIndicator, defaultStyle, 0)
        val referenceIndicatorWidth =
            ta.getDimension(R.styleable.ConsultantIndicator_consultant_indicator_width, 0f)
        val referenceIndicatorHeight =
            ta.getDimension(R.styleable.ConsultantIndicator_consultant_indicator_height, 0f)
        indicatorWidth = referenceIndicatorWidth.toInt()
        indicatorHeight = referenceIndicatorHeight.toInt()
        ta.recycle()
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        initView(context)
    }

    private fun initView(context: Context?) {
        val contentView =
            LayoutInflater.from(context).inflate(R.layout.layout_consultant_indicator, this, true)
        tvInnerIndicator = contentView.findViewById(R.id.img_consultant_indicator_inner)
        indicatorParent = contentView.findViewById(R.id.lin_consultant_indicator)
        val layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        layoutParams.width = indicatorWidth
        layoutParams.height = indicatorHeight
        tvInnerIndicator.layoutParams = layoutParams
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)
        setWidth()
    }

    private fun setWidth() {
        indicatorWidth = tvInnerIndicator.width
        indicatorParentWidth = indicatorParent.width
        // 获取每一次移动的距离
        moveLength = (indicatorParentWidth - indicatorWidth) / (listSize - 1)
    }

    /**
     * 设置与viewpager联动
     */
    fun setUpWithViewpager(viewPager: ViewPager) {
        /**
         * 保存的X值
         */
        var savedX = 0

        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                var endX = moveLength * position
                // 限制边界
                if (endX + indicatorWidth > indicatorParentWidth)
                    endX = indicatorParentWidth - indicatorWidth
                if (endX < 0) endX = 0
                translate(savedX, endX)
                savedX = endX
            }

        })
    }

    /**
     * 设置数量
     */
    fun setListSize(size: Int) {
        this.listSize = size
    }

    private fun translate(startX: Int, endX: Int) {
        val animator = ObjectAnimator.ofInt(startX, endX)
        animator.addUpdateListener { animation ->
            val point = animation?.animatedValue
            val format = BigDecimal(java.lang.String.valueOf(point)).toString()
            val pointFloat = java.lang.Float.valueOf(format)
            tvInnerIndicator.translationX = pointFloat
        }
        animator.duration = 100
        animator.start()
    }

}