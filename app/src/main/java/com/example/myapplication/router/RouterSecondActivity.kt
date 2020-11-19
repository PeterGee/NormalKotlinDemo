package com.example.myapplication.router

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.example.lib_base.constant.ExtraKeyConstant
import com.example.lib_base.constant.PathConstant
import com.example.myapplication.R
import kotlinx.android.synthetic.main.activity_router_second.*

/**
 * @date 2020/4/28
 * @author qipeng
 * @desc
 */
@Route(path = PathConstant.APP_MODULE_ROUTER_SECOND_ACTIVITY)
class RouterSecondActivity :AppCompatActivity(){
    @Autowired(name = ExtraKeyConstant.KEY_STRING_EXTRA)
    @JvmField
    var dataText:String=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ARouter.getInstance().inject(this)
        setContentView(R.layout.activity_router_second)
        initView()
    }

    private fun initView() {
        tv_show_text.text=dataText
    }
}