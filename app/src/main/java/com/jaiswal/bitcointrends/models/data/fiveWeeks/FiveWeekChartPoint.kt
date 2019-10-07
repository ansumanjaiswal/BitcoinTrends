package com.jaiswal.bitcointrends.models.data.fiveWeeks

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jjoe64.graphview.series.DataPoint

@Entity
data class FiveWeekChartPoint(
    @PrimaryKey(autoGenerate = true)
    var id: Int,

    @ColumnInfo(name = "x-coordinate") var x: Double,
    @ColumnInfo(name = "y-coordinate") var y: Double
){
    constructor(): this(0, 0.0, 0.0)

    fun getDataPoint(): DataPoint? {
        if(x!= null && y!= null){
            return DataPoint(x!!, y!!)
        }
        return null
    }
}