package com.example.myapplication.jetpackdemo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * @date 2020/10/12
 * @author qipeng
 * @desc
 */
class ViewModelTest : ViewModel() {

    private var mMutableLiveData: MutableLiveData<String>? = null

    fun getName(): LiveData<String> {
        if (null == mMutableLiveData) {
            mMutableLiveData = MutableLiveData<String>()
        }
        setName()
        return mMutableLiveData as MutableLiveData<String>
    }

    private fun setName() {
        mMutableLiveData?.value = "ViewModelTest"
    }
}