package com.example.myapplication.flowLayout

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.myapplication.R
import com.example.myapplication.databinding.ItemFlowLayoutBinding

/**
 * @author qipeng
 * @date 2019/11/28
 * @desc  viewHolder
 */
class FlowLayoutAdapter : RecyclerView.Adapter<FlowLayoutAdapter.FlowLayoutHolder> {

    private var mContext: Context
    private var data: List<Int>

    constructor(context: Context, data: List<Int>) {
        this.mContext = context
        this.data = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlowLayoutHolder {
        return FlowLayoutHolder(
            LayoutInflater.from(mContext).inflate(
                R.layout.item_flow_layout,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: FlowLayoutHolder, position: Int) {
        holder.binding.imgItem.setImageResource(data[position])
    }


    class FlowLayoutHolder : RecyclerView.ViewHolder {
        var binding: ItemFlowLayoutBinding

        constructor(v: View) : super(v) {
            binding = DataBindingUtil.bind(v)!!
        }
    }

}