package com.example.myapplication.viewpager2
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.example.myapplication.R

/**
 * @date 2020/7/22
 * @author qipeng
 * @desc
 */
class ViewPagerTwoActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_viewpager_two)

        // 普通adapter
        // viewpager_two.adapter= ViewpagerTwoAdapter()

        // 结合fragment使用
        // viewpager_two.adapter = FragmentViewPagerTwoAdapter(this)

        //结合pagerTransformer
        // viewpager_two.setPageTransformer(ZoomPagerTransformer())
         // viewpager_two.setPageTransformer(DepthPageTransformer())

        // 多个transformer结合使用
       /* val compositePageTransformer = CompositePageTransformer()
        compositePageTransformer.addTransformer(DepthPageTransformer())
        // marginTransformer
        compositePageTransformer.addTransformer(MarginPageTransformer(10))
        viewpager_two.setPageTransformer(compositePageTransformer)*/


       // viewpager_two.orientation=ViewPager2.ORIENTATION_HORIZONTAL
       //  viewpager_two.orientation=ViewPager2.ORIENTATION_VERTICAL

        // 结合tableLayout使用
       /* TabLayoutMediator(tab_layout, viewpager_two) { tab, position ->
            tab.text = "Tab ${(position + 1)}"
        }.attach()*/

    }
}