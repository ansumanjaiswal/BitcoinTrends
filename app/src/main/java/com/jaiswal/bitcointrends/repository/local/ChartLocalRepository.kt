package com.jaiswal.bitcointrends.repository.local

import androidx.lifecycle.LiveData
import com.jaiswal.bitcointrends.ChartDataBase
import com.jaiswal.bitcointrends.models.data.fiveWeeks.FiveWeekChartPoint
import com.jaiswal.bitcointrends.repository.ChartRepository

class ChartLocalRepository(private val dataBase: ChartDataBase) : ChartRepository {

    override fun getChartData(): LiveData<List<FiveWeekChartPoint>> {
        return dataBase.fiveWeekDao().getAll()
    }
}