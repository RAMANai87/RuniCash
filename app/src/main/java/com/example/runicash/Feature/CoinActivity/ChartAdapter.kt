package com.example.runicash.Feature.CoinActivity

import com.example.runicash.apiManager.model.ChartData
import com.robinhood.spark.SparkAdapter

class ChartAdapter(private val historiCallData :List<ChartData.Data>,
                   private val baseLine: String) :SparkAdapter(){
    override fun getCount(): Int {
        return historiCallData.size
    }

    override fun getItem(index: Int): ChartData.Data {
        return historiCallData[index]
    }

    override fun getY(index: Int): Float {
        return historiCallData[index].close.toFloat()
    }

    override fun hasBaseLine(): Boolean {
        return true
    }

    override fun getBaseLine(): Float {
        return baseLine?.toFloat() ?: super.getBaseLine()
    }

}