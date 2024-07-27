package com.example.myapplication.jetpack_demo.liveData.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.R
import com.example.myapplication.jetpack_demo.liveData.model.NameViewModel
import kotlinx.android.synthetic.main.activity_name.*

/**
 * @date 2020/3/31
 * @author qipeng
 * @desc
 */
class NameActivity : AppCompatActivity() {
    private lateinit var nameModel: NameViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_name)

        // 获取viewModel
        nameModel = ViewModelProvider(this)[NameViewModel::class.java]

        val nameObserver = Observer<String> { newName ->
            tv_user_name.text = newName
        }
        nameModel.currentName.observe(this, nameObserver)

        // 点击改变name
        tv_change_name.setOnClickListener {
            val anotherName = "Jim Green"
            nameModel.currentName.value = anotherName
        }
    }
}