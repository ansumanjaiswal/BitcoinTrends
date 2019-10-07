package com.jaiswal.bitcointrends.models.response

import com.google.gson.annotations.SerializedName
import com.jjoe64.graphview.series.DataPoint

class ChartCoordinates {
    @field:SerializedName("x")
    val x: Double? = null

    @field:SerializedName("y")
    val y: Double? = null

    fun getDataPoint(): DataPoint? {
        if(x!= null && y!= null){
            return DataPoint(x!!, y!!)
        }
        return null
    }
}