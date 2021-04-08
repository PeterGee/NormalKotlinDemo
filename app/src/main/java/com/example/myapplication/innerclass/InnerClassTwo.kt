package com.example.myapplication.innerclass

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R

/**
 * @date 2021/4/8
 * @author qipeng
 * @desc
 */
class InnerClassTwo:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inner_class)
    }


    interface IFaceOne{
        fun faceClicked()
    }
}