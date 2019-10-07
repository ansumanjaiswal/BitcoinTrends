package com.jaiswal.bitcointrends.repository

import androidx.lifecycle.LiveData
import com.jaiswal.bitcointrends.models.data.fiveWeeks.FiveWeekChartPoint

interface ChartRepository : Repository {
    fun getChartData(): LiveData<List<FiveWeekChartPoint>>
}