package com.example.myapplication.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R

/**
 * @Author qipeng
 * @Date 2022/8/25
 * @Desc
 */
class RecyclerTestAdapter(dateList: List<String>) : RecyclerView.Adapter<MyHolder>() {

    private var mDataList = dateList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        return MyHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_recycler_test,null))
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.itemView.visibility=View.GONE
        holder.tvText.text="测试数据"
    }

    override fun getItemCount(): Int {
        return mDataList.size
    }


}

class MyHolder(v: View) : RecyclerView.ViewHolder(v) {
    val tvText:TextView=v.findViewById(R.id.tv_recycler_item)
}