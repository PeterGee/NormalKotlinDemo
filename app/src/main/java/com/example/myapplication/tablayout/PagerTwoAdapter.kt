package com.example.myapplication.tablayout

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

/**
 * @date 2021/5/19
 * @author qipeng
 * @desc
 */
class PagerTwoAdapter(list: List<Fragment>,fragmentActivity: FragmentActivity):FragmentStateAdapter(fragmentActivity) {
    private val mList=list
    override fun getItemCount(): Int {
        return mList.size
    }

    override fun createFragment(position: Int): Fragment {
       return mList[position]
    }
}