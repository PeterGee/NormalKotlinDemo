package com.example.myapplication.jetpack_demo.dataBinding.model

import androidx.databinding.ObservableField

/**
 * @date 2020/3/30
 * @author qipeng
 * @desc
 */
data class UserModel(
    var name: String, var age: ObservableField<Int>,
    val sex: ObservableField<String>
)
