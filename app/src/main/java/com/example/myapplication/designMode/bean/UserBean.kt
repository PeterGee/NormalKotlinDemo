package com.example.myapplication.designMode.bean

import java.io.Serializable

/**
 * @date 2021/12/16
 * @author qipeng
 * @desc
 */
data class UserBean(val name:String,val age:Int?=null,var isSelected:Boolean?=false):Serializable