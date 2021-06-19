package com.example.myapplication.refreshlayout

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.myapplication.R
import kotlinx.android.synthetic.main.activity_refresh.*

/*
 * @date 2021/5/28 
 * @author qipeng
 * @desc
 */
class RefreshTestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_refresh)

        val mLayoutManager = LinearLayoutManager(this).apply {
            orientation = LinearLayoutManager.VERTICAL
        }

        val mLayoutManager2 = LinearLayoutManager(this).apply {
            orientation = LinearLayoutManager.VERTICAL
        }

        val listAll = mutableListOf<ItemBean>(ItemBean(ItemBean.TYPE_ONE, "java"),
            ItemBean(ItemBean.TYPE_TWO, "kotlin"))
        val mAdapter = MyAdapter(listAll)
        recyclerView.layoutManager = mLayoutManager
        recyclerView.adapter = mAdapter

        val mAdapter2 = MyAdapter(listAll)
        recyclerView2.layoutManager = mLayoutManager2
        recyclerView2.adapter = mAdapter2


        refreshLayout.setOnRefreshListener {
            Handler().postDelayed(Runnable {
                Log.d("peter", "refresh")
                refreshLayout.finishRefresh()
            }, 100)
        }
        refreshLayout.setEnableLoadMore(false)
        refreshLayout.setOnLoadMoreListener {
            mAdapter.addData(mAdapter.itemCount - 1, listAll)
            Handler().postDelayed(Runnable {
                Log.d("peter", "loadMore")
                refreshLayout.finishLoadMore()
            }, 100)
        }

        refreshLayout2.setOnRefreshListener {
            Handler().postDelayed(Runnable {
                Log.d("peter", "refresh")
                refreshLayout2.finishRefresh()
            }, 100)
        }
        refreshLayout2.setOnLoadMoreListener {
            mAdapter2.addData(mAdapter.itemCount - 1, listAll)
            Handler().postDelayed(Runnable {
                Log.d("peter", "loadMore")
                refreshLayout2.finishLoadMore()
            }, 100)
        }
    }

    inner class MyAdapter(list: MutableList<ItemBean>) :
        BaseMultiItemQuickAdapter<ItemBean, BaseViewHolder>(list) {

        init {
            addItemType(ItemBean.TYPE_ONE, R.layout.item_refresh_layout)
            addItemType(ItemBean.TYPE_TWO, R.layout.item_refresh_layout)
            addItemType(ItemBean.TYPE_THREE, R.layout.item_refresh_layout)
            addItemType(ItemBean.TYPE_FOUR, R.layout.item_refresh_layout)
        }

        override fun convert(helper: BaseViewHolder, item: ItemBean) {
            when (helper.itemViewType) {
                ItemBean.TYPE_ONE, ItemBean.TYPE_TWO, ItemBean.TYPE_THREE, ItemBean.TYPE_FOUR -> {
                    val tv=helper.getView<TextView>(R.id.tvItem)
                    tv.text=item.name
                }
            }
        }

    }


}

