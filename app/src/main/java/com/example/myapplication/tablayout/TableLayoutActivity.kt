package com.example.myapplication.tablayout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.example.myapplication.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_layout.*

/**
 * @date 2021/5/19
 * @author qipeng
 * @desc tableLayout
 */
class TableLayoutActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_layout)
        vp_two.adapter = PagerTwoAdapter(getFragmentList(), this)
        TabLayoutMediator(table, vp_two, false, false) { tab, position ->
            tab.customView = createCustomView(position)
        }.attach()

        // 默认选中第一个
        table.getTabAt(0).apply {
            setSelected(this, true)
        }

        // 切换监听
        table.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                setSelected(tab, true)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                setSelected(tab, false)
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })

    }

    // setSelectedState
    private fun setSelected(tab: TabLayout.Tab?, b: Boolean) {
        when (tab?.position) {
            1 -> {
                val img = tab.customView?.findViewById(R.id.imgTitle) as ImageView
                if (b) img.setBackgroundColor(resources.getColor(R.color.colorPrimary))
                else img.setBackgroundColor(resources.getColor(R.color.color_black))
            }
            else -> {
                tab?.customView?.run {
                    val mainTitle: TextView = findViewById(R.id.tvMainTitle)
                    val subTitle: TextView = findViewById(R.id.tvSubTitle)
                    if (b) {
                        mainTitle.setTextColor(resources.getColor(R.color.colorAccent))
                        subTitle.setTextColor(resources.getColor(R.color.colorAccent))
                    } else {
                        mainTitle.setTextColor(resources.getColor(R.color.color_black))
                        subTitle.setTextColor(resources.getColor(R.color.color_black))
                    }
                }
            }
        }
    }

    // 渲染view
    private fun createCustomView(position: Int): View {
        return LayoutInflater.from(this).run {
            if (position == 1) {
                inflate(R.layout.item_custom_img, null)
            } else {
                inflate(R.layout.item_custom_tab, null)
            }
        }
    }

    // 获取list
    private fun getFragmentList(): List<Fragment> {
        val mFragmentList = mutableListOf<Fragment>()
        for (i in 0..10) {
            mFragmentList.add(FragmentPagerInner())
        }
        return mFragmentList
    }
}