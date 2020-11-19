package com.example.myapplication.customView

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.example.myapplication.R

/**
 * @date 2019/10/15
 * @author qipeng
 * @desc adapter
 */
class ConsultantAdapter(data: List<Int>, context: Context) : PagerAdapter() {
    private var consultData: List<Int>? = data
    private var mContext: Context = context

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun getCount(): Int {
        return consultData?.size!!
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val innerView = View.inflate(mContext,
            R.layout.item_consultant_viewpager, null)
        val imgConsultantItem = innerView.findViewById<ImageView>(R.id.img_consultant_item)
        val tvIndex = innerView.findViewById<TextView>(R.id.tv_consultant_index)
        consultData?.get(position)?.let { imgConsultantItem.setImageResource(it) }
        tvIndex.text = "索引${position + 1}"
        container.addView(innerView)
        return innerView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        return container.removeView(`object` as View?)
    }
}