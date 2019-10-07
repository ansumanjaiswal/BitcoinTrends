package com.jaiswal.bitcointrends.repository.remote

import android.os.Handler
import android.os.Message
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.jaiswal.bitcointrends.ChartDataBase
import com.jaiswal.bitcointrends.api.BitcoinApi
import com.jaiswal.bitcointrends.api.ChartsService
import com.jaiswal.bitcointrends.models.ChartData
import com.jaiswal.bitcointrends.models.data.fiveWeeks.FiveWeekChartPoint
import com.jaiswal.bitcointrends.repository.ChartRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ChartRemoteRepository(private var dataBase: ChartDataBase) : ChartRepository {

    private var apiClient: ChartsService? = null
    private val mutableLiveData = MutableLiveData<List<FiveWeekChartPoint>>()

    override fun getChartData(): MutableLiveData<List<FiveWeekChartPoint>> {
        apiClient = BitcoinApi.client.create(ChartsService::class.java)

        val call = apiClient?.getChart(
            timespan = 5.toString() + "weeks",
            rollingAverage = 8.toString() + "hours",
            average = "json"
        )

        call?.enqueue(object : Callback<ChartData> {
            override fun onFailure(call: Call<ChartData>?, t: Throwable?) {
                Log.d("countyname", t.toString())
            }

            override fun onResponse(call: Call<ChartData>?, response: Response<ChartData>?) {
                if (response?.isSuccessful!!) {

                    val data: List<FiveWeekChartPoint> = convertData(response.body())
                    insertData(data)
                    mutableLiveData.postValue(data)
                }
            }
        })
        return mutableLiveData
    }

    private fun convertData(chartData: ChartData?): List<FiveWeekChartPoint> {
        var returnData: MutableList<FiveWeekChartPoint> = ArrayList()
        if (chartData != null) {
            for (item in chartData.values!!) {
                if (item != null) {
                    val graphData = FiveWeekChartPoint()
                    graphData.x = item.x!!
                    graphData.y = item.y!!
                    returnData.add(graphData)
                }
            }
        }
        return returnData
    }

    /*
    * Inserting data in db on a background thread
    * Can use AsyncTask
    * */
    private fun insertData(data: List<FiveWeekChartPoint>) {
        val mHandlerThread = DbUpdateHandler()
        Thread(Runnable {
            dataBase.fiveWeekDao().insertAll(*data.toTypedArray())
            val message = Message()
            message.arg1 = 5
            mHandlerThread.sendMessage(message)

        }).also { it.start() }

    }

    class DbUpdateHandler : Handler() {

        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
            println("***DB UPDATED***")
        }
    }
}