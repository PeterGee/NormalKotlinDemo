package com.example.myapplication.router

import android.content.Intent
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
class RouterSecondActivity : AppCompatActivity() {

    @Autowired(name = ExtraKeyConstant.KEY_STRING_EXTRA)
    @JvmField
    var text = ""

    @Autowired(name = ExtraKeyConstant.KEY_INT_EXTRA)
    @JvmField
    var intParam = 0

    @Autowired(name = ExtraKeyConstant.KEY_BOOLEAN_EXTRA)
    @JvmField
    var booleanParam = false

    @Autowired(name = ExtraKeyConstant.KEY_LONG_EXTRA)
    @JvmField
    var longParam = 0

    @Autowired(name = ExtraKeyConstant.KEY_SERIALIZABLE_EXTRA)
    @JvmField
    var mPerson: PersonEntity? = null

    @Autowired(name = ExtraKeyConstant.KEY_OBJECT_EXTRA)
    @JvmField
    var mList: List<PersonEntity>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 注意一定要注入才能接收到参数
        ARouter.getInstance().inject(this)
        setContentView(R.layout.activity_router_second)
        initData()
    }

    private fun initData() {
        setResultData()
        btn_second_text.text =
            "String data is $text int data is $intParam  long data is $longParam boolean data is $booleanParam "
        // 接收object参数
        btn_second_obj_text.text = mPerson?.name + mPerson?.age
        // 接收list
        btn_second_list_text.text =
            if (mList.isNullOrEmpty()) "emptyList" else "list size is ${mList?.size}"
    }

    /**
     * 设置返回数据
     */
    private fun setResultData() {
        val intent = Intent()
        intent.putExtra(ExtraKeyConstant.KEY_SECOND_ACTIVITY_EXTRA, "secondActivityData")
        setResult(RouterMainActivity.RESULT_CODE, intent)
    }
}