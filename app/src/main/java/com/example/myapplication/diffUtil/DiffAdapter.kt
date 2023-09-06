package com.example.myapplication.diffUtil

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R

/**
 * @Author qipeng
 * @Date 2023/9/6
 * @Desc
 */
class DiffAdapter(oldList: MutableList<String>,newList:MutableList<String>):RecyclerView.Adapter<DiffAdapter.MyHolder>() {
   private var mOldList:MutableList<String>
   private var mNewList:MutableList<String>
    init {
       mOldList = oldList
       mNewList = newList
   }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
       val mView = LayoutInflater.from(parent.context).inflate(R.layout.item_diff,parent,false)
       return MyHolder(mView)
    }

    override fun getItemCount(): Int {
       return mNewList.size
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.tvDiff.text = mNewList[position]
    }

     fun updateListData(newList:MutableList<String>){
        val mDiffCallBack = object : DiffUtil.Callback() {
            override fun getOldListSize(): Int {
                return mOldList.size
            }

            override fun getNewListSize(): Int {
                return newList.size
            }

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return mOldList[oldItemPosition] == newList[newItemPosition]
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return mOldList[oldItemPosition] == newList[newItemPosition]
            }

        }
        val mResult = DiffUtil.calculateDiff(mDiffCallBack)
        mResult.dispatchUpdatesTo(this)
    }

    class  MyHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
        val tvDiff:TextView = itemView.findViewById(R.id.tv_diff_item)
    }
}