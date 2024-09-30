package com.example.myapplication.viewbinding

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.AdapterViewBindingBinding

/**
 * @Author qipeng
 * @Date 2024/9/30
 * @Desc viewBinding adapter
 */
class ViewBindingAdapter:RecyclerView.Adapter<MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
       val binding = AdapterViewBindingBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.btnAdapter.text = "我是第$position"
    }

    override fun getItemCount(): Int {
        return 10
    }
}

class MyViewHolder(val binding:AdapterViewBindingBinding): RecyclerView.ViewHolder(binding.root) {

}