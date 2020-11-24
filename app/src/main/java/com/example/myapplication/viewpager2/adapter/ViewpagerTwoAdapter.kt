package com.example.myapplication.viewpager2.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R

/**
 * @date 2020/7/22
 * @author qipeng
 * @desc viewpager2Adapter
 */
class ViewpagerTwoAdapter : RecyclerView.Adapter<ViewpagerTwoAdapter.MyHolder>() {

    private val imgArray = intArrayOf(R.drawable.dog01, R.drawable.dog02, R.drawable.dog03)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        return MyHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_view_pager2, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return 10
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.ivItem.setImageResource(imgArray[position % imgArray.size])
    }


    class MyHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivItem: ImageView = view.findViewById(R.id.iv_item)
    }
}