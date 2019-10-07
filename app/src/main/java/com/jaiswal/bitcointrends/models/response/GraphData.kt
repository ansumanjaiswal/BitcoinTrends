package com.jaiswal.bitcointrends.models.response

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jaiswal.bitcointrends.models.data.fiveWeeks.FiveWeekChartPoint

class GraphData(var isDataIncoming: Boolean = false, var data: LiveData<List<FiveWeekChartPoint>> = MutableLiveData()) {

}