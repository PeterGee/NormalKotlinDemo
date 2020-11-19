package com.example.myapplication.circlePicture

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.example.myapplication.R
import com.example.myapplication.circlePicture.widget.GlideRoundTransformHelper
import kotlinx.android.synthetic.main.activity_circle_picture.*

/**
 * @date 2019/12/10
 * @author qipeng
 * @desc 圆角图片
 */
class CirclePictureActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_circle_picture)
        initView()
    }

    private fun initView() {

        val pictureDataList = listOf(
            R.drawable.dog05,
            R.drawable.dog01,
            R.drawable.dog02,
            R.drawable.dog03,
            R.drawable.dog04
        )
        val gridLayoutManager = GridLayoutManager(this, 3)
        rv_picture.layoutManager = gridLayoutManager
        rv_picture.adapter = PicAdapter(this, pictureDataList)
        wv_picture.loadUrl("")

    }

}

class PicAdapter : RecyclerView.Adapter<MyHolder> {
    private var mContext: Context
    private var mList: List<Int>

    constructor(context: Context, list: List<Int>) {
        this.mContext = context
        this.mList = list
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        return MyHolder(LayoutInflater.from(mContext).inflate(R.layout.item_picture_view, parent,false))
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        Glide.with(mContext).load(this.mList[position])
            .transform(CenterCrop(mContext), GlideRoundTransformHelper(mContext, 10))
            .into(holder.imgPictureItem)
    }

}

class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val imgPictureItem: ImageView = itemView.findViewById(R.id.img_picture_item)
}