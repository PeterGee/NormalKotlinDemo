package com.example.myapplication.jetpack_demo.liveData.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * @date 2020/3/31
 * @author qipeng
 * @desc nameViewModel
 */
class NameViewModel : ViewModel() {
    val currentName: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
}