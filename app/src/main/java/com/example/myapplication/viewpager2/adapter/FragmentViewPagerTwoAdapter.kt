package com.example.myapplication.viewpager2.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.myapplication.viewpager2.NormalFragment

/**
 * @date 2020/7/22
 * @author qipeng
 * @desc viewpager2FragmentAdapter
 */
class FragmentViewPagerTwoAdapter(frag:FragmentActivity) :FragmentStateAdapter(frag){

    override fun getItemCount(): Int {
        return 5
    }

    override fun createFragment(position: Int): Fragment {
        return NormalFragment()
    }


}