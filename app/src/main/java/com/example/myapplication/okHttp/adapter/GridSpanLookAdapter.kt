package com.example.myapplication.okHttp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.okHttp.bean.Person

/**
 * @date 2021/12/2
 * @author qipeng
 * @desc
 */
class GridSpanLookAdapter(dataList:MutableList<Person>):RecyclerView.Adapter<GridLookHolder>() {

    private val mDataList=dataList
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GridLookHolder {
        return GridLookHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_grid_look_test,null))
    }

    override fun onBindViewHolder(holder: GridLookHolder, position: Int) {
        holder.tvText.text=mDataList[position].name
    }

    override fun getItemCount(): Int {
        return mDataList.size
    }

}

class GridLookHolder(v:View):RecyclerView.ViewHolder(v){
    val tvText:TextView=v.findViewById(R.id.tv_grid_item)
}