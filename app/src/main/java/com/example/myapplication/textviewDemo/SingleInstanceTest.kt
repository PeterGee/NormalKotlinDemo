package com.example.myapplication.textviewDemo

/**
 * @Author qipeng
 * @Date 2023/5/29
 * @Desc
 */
object SingleInstanceTest {
    fun getName():String{
        return "张三"
    }
}

// 线程安全懒汉式
class SafeLazyModeInstance private constructor(){
    companion object{
        private var safeModeInstance:SafeLazyModeInstance?=null
        get() {
            if (field == null){
                field = SafeLazyModeInstance()
            }
            return field
        }

        @Synchronized
        fun get():SafeLazyModeInstance{
            return safeModeInstance!!
        }

    }
}
// 双重锁
class SingleTest private constructor(){
    companion object{
        val singleInstance:SingleTest by lazy(mode=LazyThreadSafetyMode.SYNCHRONIZED) { SingleTest() }
    }
}