package com.example.myapplication.router

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.callback.NavigationCallback
import com.alibaba.android.arouter.launcher.ARouter
import com.example.lib_base.constant.ExtraKeyConstant
import com.example.lib_base.constant.PathConstant
import com.example.myapplication.R
import kotlinx.android.synthetic.main.activity_router_main.*

/**
 * @date 2020/4/28
 * @author qipeng
 * @desc
 */
class RouterMainActivity : AppCompatActivity() {
    private val TAG="peter"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_router_main)
        initView()
    }

    private fun initView() {
        // 不带参数
        btn_skip.setOnClickListener {
            ARouter.getInstance().build(PathConstant.APP_MODULE_ROUTER_SECOND_ACTIVITY).navigation()
        }
        // 带基本类型参数
        btn_skip_two.setOnClickListener {
            ARouter.getInstance().build(PathConstant.APP_MODULE_ROUTER_SECOND_ACTIVITY)
                .withString(ExtraKeyConstant.KEY_STRING_EXTRA, "ARouter")
                .withInt(ExtraKeyConstant.KEY_INT_EXTRA, 10)
                .withLong(ExtraKeyConstant.KEY_LONG_EXTRA, 1)
                .withBoolean(ExtraKeyConstant.KEY_BOOLEAN_EXTRA, true)
                .navigation()
        }
        // 传递序列化对象
        btn_skip_with_obj.setOnClickListener {
            ARouter.getInstance().build(PathConstant.APP_MODULE_ROUTER_SECOND_ACTIVITY)
                .withSerializable(
                    ExtraKeyConstant.KEY_SERIALIZABLE_EXTRA,
                    PersonEntity("peter", 18)
                )
                .navigation()
        }

        // 传递object
        btn_skip_with_list.setOnClickListener {
            val list = ArrayList<PersonEntity>()
            list.add(PersonEntity("peter", 18))
            list.add(PersonEntity("tommy", 20))
            ARouter.getInstance().build(PathConstant.APP_MODULE_ROUTER_SECOND_ACTIVITY)
                .withObject(ExtraKeyConstant.KEY_OBJECT_EXTRA, list)
                .navigation()
        }

        // 获取跳转过程监听
        btn_skip_with_result.setOnClickListener {
            val list = ArrayList<PersonEntity>()
            list.add(PersonEntity("peter", 18))
            list.add(PersonEntity("tommy", 20))
            ARouter.getInstance().build(PathConstant.APP_MODULE_ROUTER_SECOND_ACTIVITY)
                .withObject(ExtraKeyConstant.KEY_OBJECT_EXTRA, list)
                .navigation(this, object : NavigationCallback {
                    override fun onLost(postcard: Postcard?) {
                        Log.d(TAG,"onLost")
                    }

                    override fun onFound(postcard: Postcard?) {
                        Log.d(TAG,"onFound")
                    }

                    override fun onInterrupt(postcard: Postcard?) {
                        Log.d(TAG,"onInterrupt")
                    }

                    override fun onArrival(postcard: Postcard?) {
                        Log.d(TAG,"onArrival")
                    }
                })
        }

        // 类似startActivityForResult
        btn_skip_with_response.setOnClickListener {
            ARouter.getInstance().build(PathConstant.APP_MODULE_ROUTER_SECOND_ACTIVITY).navigation(this,
                REQUEST_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode== REQUEST_CODE&&resultCode== RESULT_CODE){
            Log.d(TAG,data?.getStringExtra(ExtraKeyConstant.KEY_SECOND_ACTIVITY_EXTRA))
        }
    }


    companion object{
        const val REQUEST_CODE=0X01
        const val RESULT_CODE=0X02
    }
}