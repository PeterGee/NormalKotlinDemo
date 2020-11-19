package com.example.myapplication.router

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.example.lib_base.constant.ExtraKeyConstant
import com.example.lib_base.constant.PathConstant
import com.example.myapplication.R
import kotlinx.android.synthetic.main.activity_arouter_test.*

/**
 * @date 2020/4/28
 * @author qipeng
 * @desc  aRouter DEMO
 */
@Route(path =PathConstant.APP_MODULE_ROUTER_TEST_ACTIVITY )
class ARouterTestActivity :AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ARouter.getInstance().inject(this)
        setContentView(R.layout.activity_arouter_test)
        initView()
    }

    private fun initView() {
        // 无数据跳转
        btn_empty_jump.setOnClickListener {
            ARouter.getInstance().build(PathConstant.APP_MODULE_ROUTER_SECOND_ACTIVITY).navigation()
        }
        // 带数据跳转
        btn_data_jump.setOnClickListener {
            ARouter.getInstance().build(PathConstant.APP_MODULE_ROUTER_SECOND_ACTIVITY).withString(ExtraKeyConstant.KEY_STRING_EXTRA,"hello").navigation()
        }
    }
}