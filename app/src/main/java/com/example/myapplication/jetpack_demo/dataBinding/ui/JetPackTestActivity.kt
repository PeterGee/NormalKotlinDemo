package com.example.myapplication.jetpack_demo.dataBinding.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableField
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityJetpackDemoBinding
import com.example.myapplication.jetpack_demo.dataBinding.model.UserModel

/**
 * @date 2020/1/13
 * @author qipeng
 * @desc
 */
class JetPackTestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dataBindingUtil: ActivityJetpackDemoBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_jetpack_demo)
            dataBindingUtil.user =
                UserModel(
                    "Jim",
                    ObservableField(10),
                    ObservableField("男")
                )
            // 指定当前activity作为lifeCycle的owner
            dataBindingUtil.lifecycleOwner=this
    }
}