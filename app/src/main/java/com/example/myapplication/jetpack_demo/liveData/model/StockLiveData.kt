package com.example.myapplication.jetpack_demo.liveData.model

import androidx.lifecycle.LiveData
import java.math.BigDecimal

/**
 * @date 2020/3/31
 * @author qipeng
 * @desc
 */
class StockLiveData(symbol: String) : LiveData<BigDecimal>() {
    private val listener = { price: BigDecimal ->
        value = price
    }

    override fun onActive() {
        // is active
    }

    override fun onInactive() {
        // inactive
    }

}